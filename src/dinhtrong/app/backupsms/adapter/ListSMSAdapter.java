package dinhtrong.app.backupsms.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import dinhtrong.app.backupsms.MainActivity;
import dinhtrong.app.backupsms.R;
import dinhtrong.app.backupsms.R.id;
import dinhtrong.app.backupsms.R.layout;
import dinhtrong.app.backupsms.database.ContactModel;
import dinhtrong.app.backupsms.entity.Contact;
import dinhtrong.app.backupsms.entity.Message;
import dinhtrong.app.backupsms.util.Utils;

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
	Hashtable<Integer, Contact> hashContact;
	ContactModel contactModel;
	public ListSMSAdapter(Context context, int textViewResourceId, List<Message> list) {
		super(context, textViewResourceId, list);
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		typefaceAddress = Typeface.createFromAsset(context.getAssets(), "DancingScript-Bold.ttf");
		typefaceBody = Typeface.createFromAsset(context.getAssets(), "PalatinoLinotype.ttf");
		typefaceDate = Typeface.createFromAsset(context.getAssets(), "amazone.ttf");
		sdf = new SimpleDateFormat(MainActivity.datePatern);
		hashContact = new Hashtable<Integer, Contact>();
		contactModel = ContactModel.getInstance(context);
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
		String name = "";
		if(mess.getContactId() > 0){
			Contact contact = hashContact.get(mess.getContactId());
			if(contact == null){
				contact = contactModel.getById(mess.getContactId()+"");
				if(contact == null){
					contact = new Contact();
					contact.setFullname(Utils.formatPhoneNumberShow(mess.getAddress()));
				}
				hashContact.put(mess.getContactId(), contact);
			}
			name = contact.getFullname();
			Log.e("adapter", name + "....trong");
		}
		else{
			name = Utils.formatPhoneNumberShow(mess.getAddress());
		}
		
		holder.txtAddress.setText(name + " (" + mess.getTotals() +")");
		holder.txtBody.setText(mess.getBody());
		holder.txtDate.setText(getDate(mess.getDate()));
		return convertView;
	}
	
	class ViewHolder{
		TextView txtAddress, txtBody, txtDate;
	}
}
