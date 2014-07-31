package dinhtrong.app.backupsms.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dinhtrong.app.backupsms.database.filter.FilterPerform;

public abstract class AppModel<T> implements IModel<T> {
	protected String TABLE_NAME;
	protected String COL_ID = "id";
	protected String COL_SERVER_ID;
	protected SQLiteDatabase db;
	protected Context mContext;
	
	protected abstract String getId(T data);
	protected abstract String getServerId(T data);
	
	@Override
	public long insert(T data) {
		return db.insert(TABLE_NAME, null, buildParams(data));
	}

	@Override
	public long update(T data) {
		return db.update(TABLE_NAME, buildParams(data), COL_ID + "=?",
				new String[] { getId(data)});
	}

	@Override
	public long updatebyServerId(T data) {
		return db.update(TABLE_NAME, buildParams(data), COL_SERVER_ID + "=?",
				new String[] { getServerId(data)});
	}

	@Override
	public long save(T data) {
		if(isExist(getServerId(data)))
			return updatebyServerId(data);
		else
			return insert(data);
	}

	@Override
	public ArrayList<T> get(FilterPerform filter) {
		ArrayList<T> list = new ArrayList<T>();
		Cursor cur;
		if(filter != null)
			cur = db.query(TABLE_NAME, filter.getColumns(), filter.getSelection(), filter.getSelectionArgs(), filter.getGroupBy(),
				filter.getHaving(), filter.getOrderBy(), filter.getLimit());
		else
			cur = db.query(TABLE_NAME, null, null, null, null, null, null, null);
		if(cur != null && cur.moveToFirst()){
			do {
				list.add(parseCursor(cur));
			} while (cur.moveToNext());
		}
		return list;
	}

	@Override
	public T getById(String id) {
		Cursor cursor = db.query(TABLE_NAME, null, COL_ID +" = ?",
				new String[] { id }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			return parseCursor(cursor);
		}
		return null;
	}

	@Override
	public T getByServerId(String serverId) {
		Cursor cursor = db.query(TABLE_NAME, null, COL_SERVER_ID +" = ?",
				new String[] { serverId }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			return parseCursor(cursor);
		}
		return null;
	}

	@Override
	public boolean delete(T data) {
		return db.delete(TABLE_NAME, COL_ID + "=" + getId(data), null) > 0;
	}

	@Override
	public void truncate() {
		db.delete(TABLE_NAME, null, null);
	}

	@Override
	public boolean isEmpty() {
		Cursor cursor = db.rawQuery("select " + COL_ID + " from " + TABLE_NAME
				+ " limit 0,1 ;", null);
		boolean isEmpty = !(cursor.getCount() > 0);
		cursor.close();
		return isEmpty;
	}

	@Override
	public boolean isExist(String server_id) {
		Cursor cursor = db.rawQuery("select " + COL_ID + " from " + TABLE_NAME
				+ " where "+ COL_SERVER_ID + " = " + server_id, null);
		boolean isExist = (cursor.getCount() > 0);
		cursor.close();
		return isExist;
	}

}
