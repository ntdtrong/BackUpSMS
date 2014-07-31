package dinhtrong.app.backupsms.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	private Context mContext;
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		ArrayList<String> arrStatement = readSchema(mContext, "database_init.txt");
		for(int i = 0, length = arrStatement.size(); i < length; i++){
			db.execSQL(arrStatement.get(i));
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("DatabaseHelper", "Upgrading DB >>>>>>>>>>>>>>>>>  " + oldVersion + " : " + newVersion);
		
		for(int i = oldVersion + 1; i <= newVersion; i++){
			switch (i) {
			case 2:
				ArrayList<String> arrStatement = readSchema(mContext, "database_version_2.txt");
				for(int j = 0, length = arrStatement.size(); j < length; j++){
					Log.e("statement", arrStatement.get(j));
					db.execSQL(arrStatement.get(j));
				}
				break;

			default:
				break;
			}
		}
		
	}
	
	private ArrayList<String> readSchema(Context ctx, String fileName) {
		ArrayList<String> arrStatement = new ArrayList<String>();
		try {
			InputStream is = ctx.getAssets().open(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			StringBuilder statement = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.equals("GO")) {
					arrStatement.add(statement.toString());
					statement = null;
					statement = new StringBuilder();
				} else {
					statement.append(line).append("\n");
				}
			}
			br.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return arrStatement;
	}

}
