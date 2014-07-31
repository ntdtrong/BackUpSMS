package dinhtrong.app.backupsms.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dinhtrong.app.backupsms.Message;

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
		return agrs;
	}

	@Override
	public Message parseCursor(Cursor cur) {
		int id = cur.getInt(cur.getColumnIndex("id"));
		String body = cur.getString(cur.getColumnIndex("body"));
		String date = cur.getString(cur.getColumnIndex("date"));
		String address = cur.getString(cur.getColumnIndex("address"));
		int type = cur.getInt(cur.getColumnIndex("type"));
		Message item = new Message(id, body, date, address, type);
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

	public ArrayList<Message> getMessage(){
		ArrayList<Message> list = new ArrayList<Message>();
		
		return list;
	}
}
