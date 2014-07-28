package dinhtrong.app.backupsms;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SMSDetailsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ArrayList<Message> arrMessage =  getIntent().getExtras().getParcelableArrayList("list_sms");
		ListSMSAdapter adapter = new ListSMSAdapter(this, 0, arrMessage);
		ListView listviewSMS = (ListView) findViewById(R.id.listSMS);
		listviewSMS.setAdapter(adapter);
	}
}
