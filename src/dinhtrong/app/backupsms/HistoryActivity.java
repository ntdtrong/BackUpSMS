package dinhtrong.app.backupsms;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import dinhtrong.app.backupsms.custom.DialogSelectFile;
import dinhtrong.app.backupsms.database.ContactModel;
import dinhtrong.app.backupsms.database.MessageModel;
import dinhtrong.app.backupsms.entity.Contact;
import dinhtrong.app.backupsms.entity.Message;
import dinhtrong.app.backupsms.util.FileAccess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends FragmentActivity implements View.OnClickListener{
	FileAccess fileAccess;
	ListView listHistories;
	View btnExport, btnImport;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		setTitle("Histories");
		listHistories = (ListView) findViewById(R.id.listHistories);
		btnExport = findViewById(R.id.btnExport);
		btnImport = findViewById(R.id.btnImport);
		
		fileAccess = new FileAccess(this);
		loadHistories();
		
		btnExport.setOnClickListener(this);
		btnImport.setOnClickListener(this);
		
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

	@Override
	public void onClick(View v) {
		if(v == btnExport){
			export();
		}
		else if(v == btnImport){
			_import();
		}
		
	}
	
	private void export(){
		ArrayList<Message> lsMessages = MessageModel.getInstance(this).get(null);
		ArrayList<Contact> lsContacts = ContactModel.getInstance(this).get(null);
		fileAccess.exportData(lsMessages, lsContacts);
	}
	
	private void _import(){
		File root = Environment.getExternalStorageDirectory();
		listFiles.clear();
		getFiles(root);
		for (File file : listFiles) {
			Log.e("file", file.getAbsolutePath());
		}
		
		if(listFiles.isEmpty()){
			
			return;
		}
		
		DialogSelectFile dialog = new DialogSelectFile(listFiles);
		dialog.show(getSupportFragmentManager(), "");
		
		
	}
	
	ArrayList<File> listFiles = new ArrayList<File>();
	FileFilter fileFilter = new FileFilter() {
		
		@Override
		public boolean accept(File pathname) {
			if(pathname.isDirectory())
				return true;
			if(pathname.getName().startsWith(FileAccess.PREFIX) && pathname.getName().endsWith(".txt"))
				return true;
			
			return false;
		}
	};
	private void getFiles(File dir){
		if(dir.isDirectory()){
			File[] files = dir.listFiles(fileFilter);
			if(files != null){
				for (File file : files) {
					if(file.isFile())
						listFiles.add(file);
					else
						getFiles(file);
				}
			}
		}
	}
}
