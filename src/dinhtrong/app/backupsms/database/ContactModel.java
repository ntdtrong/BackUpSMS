package dinhtrong.app.backupsms.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import dinhtrong.app.backupsms.database.filter.FilterPerform;
import dinhtrong.app.backupsms.entity.Contact;

public class ContactModel extends AppModel<Contact>{
	private static ContactModel instance;
	
	public ContactModel(Context context) {
		TABLE_NAME = "contacts";
		COL_SERVER_ID = "id";
		db = MainDAO.getInstance(context).getDatabase();
	}

	public static ContactModel getInstance(Context context) {
		if (instance == null)
			instance = new ContactModel(context);
		return instance;
	}
	
	@Override
	public void insertMultiple(ArrayList<Contact> list) {
		String query = "INSERT INTO " + TABLE_NAME + " (id, phone_number, email, fullname) VALUES (?,?,?,?) ";
		SQLiteStatement st = db.compileStatement(query);
		db.beginTransaction();
		for (Contact contact : list) {
			st.bindLong(1, contact.getId());
			st.bindString(2, contact.getPhoneNumber());
			st.bindString(3, contact.getEmail());
			st.bindString(4, contact.getFullname());
			
			st.execute();
			st.clearBindings();
		}
		
		db.setTransactionSuccessful();
		db.endTransaction();
		
	}

	@Override
	public ContentValues buildParams(Contact data) {
		ContentValues agrs = new ContentValues();
		agrs.put("id", data.getId());
		agrs.put("phone_number", data.getPhoneNumber());
		agrs.put("email", data.getEmail());
		agrs.put("fullname", data.getFullname());
		return agrs;
	}

	@Override
	public Contact parseCursor(Cursor cur) {
		Contact item = new Contact();
		item.setId(cur.getInt(cur.getColumnIndex("id")));
		item.setFullname(cur.getString(cur.getColumnIndex("fullname")));
		item.setEmail(cur.getString(cur.getColumnIndex("email")));
		item.setPhoneNumber(cur.getString(cur.getColumnIndex("phone_number")));
		return item;
	}

	@Override
	protected String getId(Contact data) {
		
		return data.getId()+"";
	}

	@Override
	protected String getServerId(Contact data) {
		// TODO Auto-generated method stub
		return data.getId()+"";
	}
	
	public Contact getByPhoneNumber(String phoneNumber){
		Contact contact = new Contact();
		contact.setId(0);
		FilterPerform filter = new FilterPerform();
		filter.setSelection("phone_number LIKE '%" + phoneNumber +"%'");
		ArrayList<Contact> list = get(filter);
		if(list != null && list.size() > 0)
			contact = list.get(0);
		
		return contact;
	}
}
