package dinhtrong.app.backupsms.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import dinhtrong.app.backupsms.entity.Message;

public class MessageModel extends AppModel<Message> {
	private static MessageModel instance;
	
	public MessageModel(Context context) {
		TABLE_NAME = "messages";
		COL_SERVER_ID = "message_id";
		db = MainDAO.getInstance(context).getDatabase();
	}

	public static MessageModel getInstance(Context context) {
		if (instance == null)
			instance = new MessageModel(context);
		return instance;
	}

	@Override
	public void insertMultiple(ArrayList<Message> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContentValues buildParams(Message data) {
		ContentValues agrs = new ContentValues();
		agrs.put("id", data.getId());
		agrs.put("address", data.getAddress());
		agrs.put("body", data.getBody());
		agrs.put("date", data.getDate());
		agrs.put("type", data.getType());
		agrs.put("contact_id", data.getContactId());
		return agrs;
	}

	@Override
	public Message parseCursor(Cursor cur) {
		int id = cur.getInt(cur.getColumnIndex("id"));
		String body = cur.getString(cur.getColumnIndex("body"));
		String date = cur.getString(cur.getColumnIndex("date"));
		String address = cur.getString(cur.getColumnIndex("address"));
		int type = cur.getInt(cur.getColumnIndex("type"));
		int contactId = cur.getInt(cur.getColumnIndex("contact_id"));
		Message item = new Message(id, body, date, address, type, contactId);
		Log.e("contact_id", contactId +"parseCursor");
		return item;
	}

	@Override
	protected String getId(Message data) {
		return data.getId()+"";
	}

	@Override
	protected String getServerId(Message data) {
		// TODO Auto-generated method stub
		return null;
	
	}
	
	public int getTotals(){
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(id) AS totals FROM ").append(TABLE_NAME);
		Cursor cur = db.rawQuery(query.toString(), null);
		if(cur != null && cur.moveToFirst()){
			return cur.getInt(cur.getColumnIndex("totals"));
		}
		return 0;
	}

	public ArrayList<Message> getMessage(){
		ArrayList<Message> list = new ArrayList<Message>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT *, COUNT(id) AS totals FROM ").append(TABLE_NAME)
		.append(" GROUP BY address ORDER BY date DESC ");
		Cursor cur = db.rawQuery(query.toString(), null);
		if(cur != null && cur.moveToFirst()){
			do {
				Message m = parseCursor(cur);
				m.setTotals(cur.getInt(cur.getColumnIndex("totals")));
				list.add(m);
			} while (cur.moveToNext());
		}
		return list;
	}
	
	int PAGE_SIZE = 20;
	public ArrayList<Message> getMessageByAddress(String address, int page){
		ArrayList<Message> list = new ArrayList<Message>();
		int start = (page - 1)*PAGE_SIZE, end = page * PAGE_SIZE; 
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ").append(TABLE_NAME)
		.append(" WHERE address='"+address+"' ")
		.append(" ORDER BY date DESC ")
		.append(" LIMIT " + start+"," + end);
		Cursor cur = db.rawQuery(query.toString(), null);
		if(cur != null && cur.moveToFirst()){
			do {
				list.add(parseCursor(cur));
			} while (cur.moveToNext());
		}
		return list;
	}
	
	
	public boolean isExist(Message data) {
		Cursor cursor = db.rawQuery("select " + COL_ID + " from " + TABLE_NAME
				+ " where id = " + data.getId() + " AND date = '" + data.getDate()+"'", null);
		boolean isExist = (cursor.getCount() > 0);
		cursor.close();
		return isExist;
	}
}
