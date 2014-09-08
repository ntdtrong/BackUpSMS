package dinhtrong.app.backupsms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import dinhtrong.app.backupsms.database.ContactModel;
import dinhtrong.app.backupsms.database.MessageModel;
import dinhtrong.app.backupsms.database.filter.FilterPerform;
import dinhtrong.app.backupsms.entity.Contact;

public class MainActivity extends FragmentActivity implements OnItemClickListener{

	FileAccess fileAccess;
	JSONObject jsAllSMS;
	SimpleDateFormat sdf;
	public static String datePatern = "d MMM, ''yy HH:mm:ss";
	
	ListView listviewSMS;
	
	MessageModel messageModel;
	ContactModel contactModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listviewSMS = (ListView) findViewById(R.id.listSMS);
		sdf = new SimpleDateFormat(datePatern);
		messageModel = MessageModel.getInstance(this);
		contactModel = ContactModel.getInstance(this);
		fileAccess = new FileAccess(this);
//		showListSMS();
		showMessageFromDB();
		
		int totals = messageModel.getTotals();
		setTitle("SMS ( totals : " + totals + ")");
		
//		readSMS();
		
		
	}
	
//	private void showListSMS(){
//		String data = fileAccess.readMainFile();
////		Log.e(tag, data);
//		ArrayList<Message> arrMessage = new ArrayList<Message>();
//		try {
//			jsAllSMS = new JSONObject(data);
//			Iterator<String> addresses = jsAllSMS.keys();
//			while (addresses.hasNext()) {
//				String address = (String) addresses.next();
//				JSONObject js = jsAllSMS.getJSONObject(address);
//				Iterator<String> ids = js.keys();
//				if(ids.hasNext()){
//					String id = (String) ids.next();
//					arrMessage.add(new Message(js.getJSONObject(id)));
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		ListSMSAdapter adapter = new ListSMSAdapter(this, 0, arrMessage);
//		listviewSMS.setAdapter(adapter);
//		listviewSMS.setOnItemClickListener(this);
//	}
	
	private void showMessageFromDB(){
		FilterPerform filter = new FilterPerform();
		filter.setGroupBy("address");
		filter.setOrderBy("id DESC");
		ArrayList<Message> arrMessage = messageModel.getMessage();
		
		ListSMSAdapter adapter = new ListSMSAdapter(this, 0, arrMessage);
		listviewSMS.setAdapter(adapter);
		listviewSMS.setOnItemClickListener(this);
	}
	
	private void readSMS(){
//		messageModel.truncate();
		Cursor cursor = getContentResolver().query(Uri.parse("content://sms/"), null, null, null, "sms._id DESC");
		cursor.moveToFirst();
//		JSONObject listSMS = new JSONObject();
		
		do{
		   try {
			   String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
			   String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
			   String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
			   String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
			   String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
			   address = validateAddress(address);
			   Contact contact = linkContact(address);
			   Log.e("id", id +" : " + date);
			   Message mess = new Message(Integer.parseInt(id), body, date, address, Integer.parseInt(type), contact.getId());
			   if(!messageModel.isExist(mess))
				   messageModel.insert(mess);
			   
			   
//			   if(listSMS.has(address)){
//				   listSMS.getJSONObject(address).put(id, mess.toJson());
//			   }
//			   else{
//				   JSONObject js = new JSONObject();
//				   js.put(id, mess.toJson());
//				   listSMS.put(address, js);
//			   }
			   
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while(cursor.moveToNext());
//		Log.e("message", listSMS.toString());
		
//		fileAccess.excuteData(listSMS);
	}
	
	Hashtable<String, Contact> hashContact;
	private Contact linkContact(String phone){
		if(hashContact == null)
			hashContact = new Hashtable<String, Contact>();
		
		if(!hashContact.containsKey(phone))
			hashContact.put(phone, contactModel.getByPhoneNumber(phone));
		
		return hashContact.get(phone);
	}
	
	private void readContact(){
		String phoneNumber = null;
		String email = null;

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;
		
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);	
		ArrayList<Contact> listContacts = new ArrayList<Contact>();
		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {

				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
				StringBuilder sbPhone = new StringBuilder();
				StringBuilder sbEmail = new StringBuilder();  
						
				Contact contact = new Contact();
				contact.setId(Integer.parseInt(contact_id));
				contact.setFullname(name);
				
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

				if (hasPhoneNumber > 0) {
					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						sbPhone.append(phoneNumber).append(",");
					}
					phoneCursor.close();

					// Query and loop for every email of the contact
					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
					while (emailCursor.moveToNext()) {
						email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
						sbEmail.append(email).append(",");
					}
					emailCursor.close();
					
				}
				
				contact.setPhoneNumber(sbPhone.toString());
				contact.setEmail(sbEmail.toString());
//				contact.show();
				listContacts.add(contact);
			}

		}
		contactModel.truncate();
		contactModel.insertMultiple(listContacts);
	}
	
	private String validateAddress(String address){
		if(address.startsWith("+84")){
			address = "0" + address.substring(3);
		}
		else if(address.startsWith("+9")){
			address = address.substring(1);
		}
		return address;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	String tag = "BackupSMS";
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int action = item.getItemId();
		switch (action) {
		case R.id.action_backup:
			Log.e(tag, "action_backup");
			backUpSMS();
			return true;
			
		case R.id.action_history:
			Intent i = new Intent(this, ListContactActivity.class);
			startActivity(i);
			return true;
			
		case R.id.action_clear:
			fileAccess.clear();
			ListSMSAdapter adapter = (ListSMSAdapter)listviewSMS.getAdapter();
			adapter.clear();
			adapter.notifyDataSetChanged();
			return true;

		default:
			return super.onMenuItemSelected(featureId, item);
		}
		
	}
	
	private void backUpSMS(){
		readContact();
		readSMS();
		showMessageFromDB();
//		showListSMS();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if(parent.getAdapter() instanceof ListSMSAdapter){
			ListSMSAdapter adapter = (ListSMSAdapter) parent.getAdapter();
			Message mess = adapter.getItem(position);
			
			Intent i = new Intent(this, SMSDetailsActivity.class);
			i.putExtra("address", mess.getAddress());
			startActivity(i);
			
//			showListSMSDetails(mess.getAddress());
		}
	}
	
//	private void showListSMSDetails(String address){
//		JSONObject json = jsAllSMS.optJSONObject(address);
//		
//		ArrayList<Message> arrMessage = new ArrayList<Message>();
//		try {
//			Iterator<String> ids = json.keys();
//			while (ids.hasNext()) {
//				String id = (String) ids.next();
//				arrMessage.add(new Message(json.getJSONObject(id)));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Collections.sort(arrMessage, new CustomCompare());
//		Log.e("size", arrMessage.size() +"");
//		Intent i = new Intent(this, SMSDetailsActivity.class);
//		i.putParcelableArrayListExtra("list_sms", arrMessage);
//		startActivity(i);
//	}

}
