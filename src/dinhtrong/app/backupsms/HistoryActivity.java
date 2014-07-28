package dinhtrong.app.backupsms;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends Activity{
	FileAccess fileAccess;
	ListView listHistories;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		setTitle("Histories");
		listHistories = (ListView) findViewById(R.id.listHistories);
		fileAccess = new FileAccess(this);
		loadHistories();
		
		listHistories.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				String fileName = parent.getItemAtPosition(position).toString();
				delete(fileName);
				return true;
			}
		});
		
	}
	
	private void loadHistories(){
		File[] files = fileAccess.dir.listFiles();
		ArrayList<CharSequence> listFileNames = new ArrayList<CharSequence>(files.length);
		for (File file : files) {
			listFileNames.add(file.getName());
		}
		
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, listFileNames);
		listHistories.setAdapter(adapter);
	}
	
	private void delete(final String file){
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.delete_confirm_title);
		dialog.setMessage(R.string.delete_confirm_message);
		dialog.setNegativeButton("No", null);
		dialog.setPositiveButton("Yes", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String path = fileAccess.dir.getAbsolutePath()+"/"+file;
				File f = new File(path);
				if(f.exists()){
					f.delete();
					loadHistories();
				}
			}
		});
		dialog.show();
	}
}
