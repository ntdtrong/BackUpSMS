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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ListDetailsAdapter extends ArrayAdapter<Message>{
	ArrayList<Message> listMessages;
	LayoutInflater li;
	Typeface typefaceAddress, typefaceBody, typefaceDate;
	LayoutParams paramsSend, paramsReceive;
	public ListDetailsAdapter(Context context, int textViewResourceId, List<Message> list) {
		super(context, textViewResourceId, list);
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		typefaceAddress = Typeface.createFromAsset(context.getAssets(), "DancingScript-Bold.ttf");
		typefaceBody = Typeface.createFromAsset(context.getAssets(), "PalatinoLinotype.ttf");
		typefaceDate = Typeface.createFromAsset(context.getAssets(), "amazone.ttf");
		paramsReceive = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		paramsSend = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		paramsReceive.setMargins(0, 0, 50, 0);
		paramsSend.setMargins(50, 0, 0, 0);
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
			holder.bg = convertView.findViewById(R.id.bg);
			holder.left = convertView.findViewById(R.id.imgLeft);
			holder.right = convertView.findViewById(R.id.imgRight);
			holder.txtDate.setTypeface(typefaceDate);
			holder.txtBody.setTypeface(typefaceBody);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtBody.setText(mess.getBody());
		holder.txtDate.setText(mess.getDate());
		
		int type = mess.getType();
		if(type == 1){
			holder.bg.setBackgroundResource(R.drawable.sms_receive);
			holder.bg.setLayoutParams(paramsReceive);
			holder.left.setVisibility(View.VISIBLE);
			holder.right.setVisibility(View.GONE);
//			((android.widget.LinearLayout.LayoutParams)((LinearLayout)convertView).getLayoutParams()).rightMargin = 100;
		}
		else{
			holder.bg.setBackgroundResource(R.drawable.sms_send);
			holder.bg.setLayoutParams(paramsSend);
			holder.left.setVisibility(View.GONE);
			holder.right.setVisibility(View.VISIBLE);
//			((android.widget.LinearLayout.LayoutParams)((LinearLayout)convertView).getLayoutParams()).leftMargin = 100;
		}
		
		Log.e("type", mess.getId() + " : " + mess.getBody());
		return convertView;
	}
	
	class ViewHolder{
		TextView  txtBody, txtDate;
		View bg, left, right;
	}
}
