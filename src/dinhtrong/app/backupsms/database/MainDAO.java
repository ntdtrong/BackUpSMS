package dinhtrong.app.backupsms.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class MainDAO {
	final String DATABASE_NAME = "smsbackup";
	final int DATABASE_VERSION = 1;
	MyDatabaseHelper dbHelper;
	SQLiteDatabase db;
	private static MainDAO instance; 
	private Context mContext;
	
	public MainDAO(Context context){
		open(context);
		mContext = context;
	}
	
	public static MainDAO getInstance(Context context){
		if(instance == null)
			instance = new MainDAO(context);
		
		return instance;
	}
	
	private void open(Context context){
		dbHelper = new MyDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	public SQLiteDatabase getDatabase(){
		return db;
	}
	
	public void clear(){
		MessageModel.getInstance(mContext).truncate();
		ContactModel.getInstance(mContext).truncate();
	}
	
//	private void close(){
//		dbHelper.close();
//	}
}
