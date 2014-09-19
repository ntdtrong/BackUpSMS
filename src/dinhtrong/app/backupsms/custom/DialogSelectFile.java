package dinhtrong.app.backupsms.custom;

import java.io.File;
import java.util.ArrayList;

import dinhtrong.app.backupsms.adapter.ListFileAdapter;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DialogSelectFile extends DialogFragment{
	ArrayList<File> list;
	public DialogSelectFile(ArrayList<File> list) {
		this.list = list;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Builder builder = new Builder(getActivity());
		ListView listview = new ListView(getActivity());
		builder.setTitle("Select File");
		builder.setView(listview);
		ListFileAdapter adapter = new ListFileAdapter(getActivity(), android.R.layout.select_dialog_item, list);
		listview.setAdapter(adapter);
		
		return builder.create();
	}
	
	
}
