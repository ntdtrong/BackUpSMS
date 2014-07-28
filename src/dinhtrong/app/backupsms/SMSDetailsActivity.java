package dinhtrong.app.backupsms;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class SMSDetailsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		setTitle("Details");
		ArrayList<Message> arrMessage =  getIntent().getExtras().getParcelableArrayList("list_sms");
		TextView txtHeader = (TextView) findViewById(R.id.txtHeader);
		if(arrMessage != null){
			Message item = arrMessage.get(0);
			txtHeader.setText(item.getAddress());
		}
		ListDetailsAdapter adapter = new ListDetailsAdapter(this, 0, arrMessage);
		ListView listviewSMS = (ListView) findViewById(R.id.listSMS);
		listviewSMS.setAdapter(adapter);
	}
}
