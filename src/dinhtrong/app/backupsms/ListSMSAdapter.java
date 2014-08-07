package dinhtrong.app.backupsms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListSMSAdapter extends ArrayAdapter<Message>{
	ArrayList<Message> listMessages;
	LayoutInflater li;
	Typeface typefaceAddress, typefaceBody, typefaceDate;
	SimpleDateFormat sdf;
	public ListSMSAdapter(Context context, int textViewResourceId, List<Message> list) {
		super(context, textViewResourceId, list);
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		typefaceAddress = Typeface.createFromAsset(context.getAssets(), "DancingScript-Bold.ttf");
		typefaceBody = Typeface.createFromAsset(context.getAssets(), "PalatinoLinotype.ttf");
		typefaceDate = Typeface.createFromAsset(context.getAssets(), "amazone.ttf");
		sdf = new SimpleDateFormat(MainActivity.datePatern);
	}
	
	private String getDate(String miliseconds){
		try {
			
//			return (String) DateFormat.format(datePatern, );
			return sdf.format(new Date(Long.parseLong(miliseconds)));
		} catch (Exception e) {
		}
		
		return "";
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Message mess = getItem(position);
		if(convertView == null){
			holder = new ViewHolder();
			convertView = li.inflate(R.layout.sms_item, null);
			holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
			holder.txtBody = (TextView) convertView.findViewById(R.id.txtBody);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
			holder.txtAddress.setTypeface(typefaceAddress);
			holder.txtDate.setTypeface(typefaceDate);
			holder.txtBody.setTypeface(typefaceBody);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtAddress.setText(mess.getAddress() + " (" + mess.getTotals() +")");
		holder.txtBody.setText(mess.getBody());
		holder.txtDate.setText(getDate(mess.getDate()));
		return convertView;
	}
	
	class ViewHolder{
		TextView txtAddress, txtBody, txtDate;
	}
}
