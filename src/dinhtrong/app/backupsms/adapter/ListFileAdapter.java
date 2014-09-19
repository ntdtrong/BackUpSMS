package dinhtrong.app.backupsms.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListFileAdapter extends ArrayAdapter<File>{
	LayoutInflater li;
	public ListFileAdapter(Context context, int resource, List<File> objects) {
		super(context, resource, objects);
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = li.inflate(android.R.layout.simple_list_item_1, parent, false);
		}
		File file = getItem(position);
		((TextView)convertView).setText(file.getName());
		return convertView;
	}

}
