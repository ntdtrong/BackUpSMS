package dinhtrong.app.backupsms.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import dinhtrong.app.backupsms.database.filter.FilterPerform;


public interface IModel<T> {
	public long insert(T data);
	public void insertMultiple(ArrayList<T> list);
	public long update(T data);
	public long updatebyServerId(T data);
	public long save(T data);
	public ContentValues buildParams(T data);
	public T parseCursor(Cursor cur);
	public ArrayList<T> get(FilterPerform filter);
	public T getById(String id);
	public T getByServerId(String serverId);
	public boolean delete(T data);
	public void truncate();
	public boolean isEmpty();
	public boolean isExist(String server_id);
}
