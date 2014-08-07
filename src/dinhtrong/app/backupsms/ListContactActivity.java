package dinhtrong.app.backupsms;

import org.apache.http.conn.scheme.LayeredSocketFactory;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ListView;

public class ListContactActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{
	
	 private final static String[] FROM_COLUMNS = {
         Build.VERSION.SDK_INT
                 >= Build.VERSION_CODES.HONEYCOMB ?
                 Contacts.DISPLAY_NAME_PRIMARY :
                 Contacts.DISPLAY_NAME
 };

 private final static int[] TO_IDS = {
        R.id.txtContactName
 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		readContact();
	}
	ListView mListview;
	
	private static final String[] PROJECTION =
        {
            Contacts._ID,
            Contacts.LOOKUP_KEY,
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    Contacts.DISPLAY_NAME_PRIMARY :
                    Contacts.DISPLAY_NAME

        };
	// The column index for the _ID column
	private static final int CONTACT_ID_INDEX = 0;
	// The column index for the LOOKUP_KEY column
	private static final int LOOKUP_KEY_INDEX = 1;
	
	private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
            Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
            Contacts.DISPLAY_NAME + " LIKE ?";
    // Defines a variable for the search string
    private String mSearchString;
    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = { mSearchString };
    SimpleCursorAdapter mCursorAdapter;
	private void readContact(){
		getSupportLoaderManager().initLoader(0, null, this);
		mListview = (ListView) findViewById(R.id.listContact);
		mCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.sms_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
		
		mListview.setAdapter(mCursorAdapter);
		
		Log.e(tag, "count : " + mCursorAdapter.getCount() + "");
	}
	String tag = "Contact";
	
	
	@Override
	public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
        mSelectionArgs[0] = "%" + mSearchString + "%";
        // Starts the query
        return new CursorLoader(
                ListContactActivity.this,
                Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
        );
    }
	@Override
	 public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        mCursorAdapter.swapCursor(cursor);
    }
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);

    }

}
