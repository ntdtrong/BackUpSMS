package dinhtrong.app.backupsms.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import dinhtrong.app.backupsms.entity.Contact;
import dinhtrong.app.backupsms.entity.Message;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SMSUtils {
	private final String C_ID 		= "_id";
	private final String C_ADDRESS 	= "address";
	private final String C_BODY 	= "body";
	private final String C_DATE 	= "date";
	private final String C_TYPE 	= "type";
	
	private Activity mActivity;
	private Uri mUri;
	public SMSUtils(Activity activity) {
		mActivity = activity;
		mUri = Uri.parse("content://sms/");
	}
	
	public void insertToPhone(Message sms){
		ContentValues values = new ContentValues();
//		values.put(C_ID, sms.getId());
		values.put(C_ADDRESS, sms.getAddress());
		values.put(C_BODY, sms.getBody());
		values.put(C_DATE, sms.getDate());
		values.put(C_TYPE, sms.getType());
		mActivity.getContentResolver().insert(mUri, values);
		
	}
	
	public void backUpFromPhone(){
		Log.e("backUpFromPhone", "start....");
		long start = System.currentTimeMillis();
		Cursor cursor = mActivity.getContentResolver().query(mUri, null, null, null, "sms._id DESC");
		cursor.moveToFirst();
		try {
			FileAccess fileUtils = new FileAccess(mActivity);
			File f = fileUtils.createFile();
			FileOutputStream fos = new FileOutputStream(f);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			do{
			   try {
				   String id = cursor.getString(cursor.getColumnIndexOrThrow(C_ID));
				   String address = cursor.getString(cursor.getColumnIndexOrThrow(C_ADDRESS));
				   String body = cursor.getString(cursor.getColumnIndexOrThrow(C_BODY));
				   String date = cursor.getString(cursor.getColumnIndexOrThrow(C_DATE));
				   String type = cursor.getString(cursor.getColumnIndexOrThrow(C_TYPE));
				   Message message = new Message(Integer.parseInt(id), body, date, address, Integer.parseInt(type), 0);
				   bw.write(message.toJson().toString());
				   bw.newLine();
				   
				} catch (Exception e) {
					e.printStackTrace();
				}
			}while(cursor.moveToNext());
			
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.e("backUpFromPhone", "end .... : " + (System.currentTimeMillis() - start)/1000 );
	}
	
}
