package dinhtrong.app.backupsms;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListDetailsAdapter extends ArrayAdapter<Message>{
	ArrayList<Message> listMessages;
	LayoutInflater li;
	Typeface typefaceAddress, typefaceBody, typefaceDate;
//	LayoutParams paramsSend, paramsReceive;
	public ListDetailsAdapter(Context context, int textViewResourceId, List<Message> list) {
		super(context, textViewResourceId, list);
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		typefaceAddress = Typeface.createFromAsset(context.getAssets(), "DancingScript-Bold.ttf");
		typefaceBody = Typeface.createFromAsset(context.getAssets(), "PalatinoLinotype.ttf");
		typefaceDate = Typeface.createFromAsset(context.getAssets(), "amazone.ttf");
//		paramsReceive = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		paramsSend = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Message mess = getItem(position);
		if(convertView == null){
			holder = new ViewHolder();
			convertView = li.inflate(R.layout.sms_item_details, null);
			holder.txtBody = (TextView) convertView.findViewById(R.id.txtBody);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
			holder.txtDate.setTypeface(typefaceDate);
			holder.txtBody.setTypeface(typefaceBody);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtBody.setText(mess.getBody());
		holder.txtDate.setText(mess.getDate());
		
		int type = 1;
		try {
			type = Integer.parseInt(mess.getType().trim());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(type == 1){
			convertView.setBackgroundResource(R.drawable.bg_sms_item_receive);
//			((android.widget.LinearLayout.LayoutParams)((LinearLayout)convertView).getLayoutParams()).rightMargin = 100;
		}
		else{
			convertView.setBackgroundResource(R.drawable.bg_sms_item_send);
//			((android.widget.LinearLayout.LayoutParams)((LinearLayout)convertView).getLayoutParams()).leftMargin = 100;
		}
		
		Log.e("type", mess.getType());
		return convertView;
	}
	
	class ViewHolder{
		TextView  txtBody, txtDate;
	}
}
