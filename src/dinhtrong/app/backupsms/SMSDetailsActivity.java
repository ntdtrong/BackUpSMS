package dinhtrong.app.backupsms;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import dinhtrong.app.backupsms.database.MessageModel;

public class SMSDetailsActivity extends Activity {
	MessageModel messageModel;
	String address;
	ListDetailsAdapter adapter;
	ListView listviewSMS;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		setTitle("Details");
		TextView txtHeader = (TextView) findViewById(R.id.txtHeader);
		
		messageModel = MessageModel.getInstance(this);
		address = getIntent().getExtras().getString("address");
		txtHeader.setText(address);
		
//		ArrayList<Message> arrMessage =  getIntent().getExtras().getParcelableArrayList("list_sms");
//		FilterPerform filter = new FilterPerform();
//		filter.setSelection(" address = '" +address+"'");
//		filter.setOrderBy("id ASC");
//		ArrayList<Message> arrMessage = messageModel.get(filter);
//		TextView txtHeader = (TextView) findViewById(R.id.txtHeader);
//		if(arrMessage != null){
//			Message item = arrMessage.get(0);
//			txtHeader.setText(item.getAddress());
//		}
//		
//		CustomCompare comparator = new CustomCompare();
//		Collections.sort(arrMessage, comparator);
		
		adapter = new ListDetailsAdapter(this, 0, new ArrayList<Message>());
		listviewSMS = (ListView) findViewById(R.id.listSMS);
		listviewSMS.setAdapter(adapter);
		
		loadData();
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				listviewSMS.setOnScrollListener(new OnScrollListener() {
					
					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {
						if(firstVisibleItem < 5 && !isLoading){
							loadData();
						}
					}
				});
				
			}
		}, 500);
	}
	
	int page = 0;
	boolean isLoading = false;
	private void loadData(){
		isLoading = true;
		page++;
		ArrayList<Message> list = messageModel.getMessageByAddress(address, page);
		
		int index = listviewSMS.getFirstVisiblePosition() + list.size();
	    View v = listviewSMS.getChildAt(0);
	    int top = (v == null) ? 0 : v.getTop();         

	    adapter.addTop(list);
	    adapter.notifyDataSetChanged();

	    listviewSMS.setSelectionFromTop(index, top);
	    
	    isLoading = false;
	}
}
