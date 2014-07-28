package dinhtrong.app.backupsms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener{

	FileAccess fileAccess;
	JSONObject jsAllSMS;
	SimpleDateFormat sdf;
	String datePatern = "d MMM, ''yy HH:mm:ss";
	
	ListView listviewSMS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listviewSMS = (ListView) findViewById(R.id.listSMS);
		sdf = new SimpleDateFormat(datePatern);
		fileAccess = new FileAccess(this);
		showListSMS();
//		readSMS();
	}
	
	private void showListSMS(){
		String data = fileAccess.readMainFile();
//		Log.e(tag, data);
		ArrayList<Message> arrMessage = new ArrayList<Message>();
		try {
			jsAllSMS = new JSONObject(data);
			Iterator<String> addresses = jsAllSMS.keys();
			while (addresses.hasNext()) {
				String address = (String) addresses.next();
				JSONObject js = jsAllSMS.getJSONObject(address);
				Iterator<String> ids = js.keys();
				if(ids.hasNext()){
					String id = (String) ids.next();
					arrMessage.add(new Message(js.getJSONObject(id)));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ListSMSAdapter adapter = new ListSMSAdapter(this, 0, arrMessage);
		listviewSMS.setAdapter(adapter);
		listviewSMS.setOnItemClickListener(this);
	}
	
	private void readSMS(){
		Cursor cursor = getContentResolver().query(Uri.parse("content://sms/"), null, null, null, "sms._id DESC");
		cursor.moveToFirst();
		JSONObject listSMS = new JSONObject();
		
		do{
		   try {
			   String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
			   String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
			   String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
			   String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
			   String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
			   Log.e("id", id +" : " + date);
			   Message mess = new Message(Integer.parseInt(id), body, getDate(date), address, type);
			   if(listSMS.has(address)){
				   listSMS.getJSONObject(address).accumulate(id, mess.toJson());
			   }
			   else{
				   JSONObject js = new JSONObject();
				   js.put(id, mess.toJson());
				   listSMS.put(address, js);
			   }
			   
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while(cursor.moveToNext());
//		Log.e("message", listSMS.toString());
		
		fileAccess.excuteData(listSMS);
	}
	
	private String getDate(String miliseconds){
		try {
			
//			return (String) DateFormat.format(datePatern, );
			return sdf.format(new Date(Long.parseLong(miliseconds)));
		} catch (Exception e) {
		}
		
		return "";
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
			Intent i = new Intent(this, HistoryActivity.class);
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
		readSMS();
		showListSMS();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if(parent.getAdapter() instanceof ListSMSAdapter){
			ListSMSAdapter adapter = (ListSMSAdapter) parent.getAdapter();
			Message mess = adapter.getItem(position);
			showListSMSDetails(mess.getAddress());
		}
	}
	
	private void showListSMSDetails(String address){
		JSONObject json = jsAllSMS.optJSONObject(address);
		
		ArrayList<Message> arrMessage = new ArrayList<Message>();
		try {
			Iterator<String> ids = json.keys();
			while (ids.hasNext()) {
				String id = (String) ids.next();
				arrMessage.add(new Message(json.getJSONObject(id)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(arrMessage, new CustomCompare());
		Log.e("size", arrMessage.size() +"");
		Intent i = new Intent(this, SMSDetailsActivity.class);
		i.putParcelableArrayListExtra("list_sms", arrMessage);
		startActivity(i);
	}

}
