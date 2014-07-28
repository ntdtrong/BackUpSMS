package dinhtrong.app.backupsms;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		ListView listHistories = (ListView) findViewById(R.id.listHistories);
		FileAccess fileAccess = new FileAccess(this);
		File[] files = fileAccess.dir.listFiles();
		ArrayList<CharSequence> listFileNames = new ArrayList<CharSequence>(files.length);
		for (File file : files) {
			listFileNames.add(file.getName());
		}
		
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, listFileNames);
		listHistories.setAdapter(adapter);
		
	}
}
