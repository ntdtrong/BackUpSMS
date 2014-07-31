package dinhtrong.app.backupsms;

import java.util.ArrayList;
import java.util.Collections;

import dinhtrong.app.backupsms.database.MessageModel;
import dinhtrong.app.backupsms.database.filter.FilterPerform;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class SMSDetailsActivity extends Activity {
	MessageModel messageModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		setTitle("Details");
		messageModel = MessageModel.getInstance(this);
		String address = getIntent().getExtras().getString("address");
		
//		ArrayList<Message> arrMessage =  getIntent().getExtras().getParcelableArrayList("list_sms");
		FilterPerform filter = new FilterPerform();
		
		filter.setSelection(" address = '" +address+"'");
		filter.setOrderBy("id ASC");
		ArrayList<Message> arrMessage = messageModel.get(filter);
		TextView txtHeader = (TextView) findViewById(R.id.txtHeader);
		if(arrMessage != null){
			Message item = arrMessage.get(0);
			txtHeader.setText(item.getAddress());
		}
		
		CustomCompare comparator = new CustomCompare();
		Collections.sort(arrMessage, comparator);
		ListDetailsAdapter adapter = new ListDetailsAdapter(this, 0, arrMessage);
		
		ListView listviewSMS = (ListView) findViewById(R.id.listSMS);
		listviewSMS.setAdapter(adapter);
	}
}
