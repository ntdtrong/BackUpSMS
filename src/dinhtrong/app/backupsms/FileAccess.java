package dinhtrong.app.backupsms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class FileAccess {
	File dir, fileMain;
	public FileAccess(Context context) {
		dir = new File(context.getExternalFilesDir(null), "backup");
		if(!dir.exists())
			dir.mkdir();
		fileMain = new File(dir, "SMS_Backup_Main.txt");
	}
	
	public void clear(){
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				Log.e("clear", file.getName() + " : " + file.delete());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String readMainFile(){
		return read(fileMain);
	}
	
	public void excuteData(JSONObject data){
		String strData = data.toString();
		String currentData = "{}";
		if(fileMain.exists())
			currentData = read(fileMain);
		
		// Write milestone backup
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		String fileName = "smsbackup_" + sdf.format(date) + ".txt";
		File f = new File(dir, fileName);
		write(strData, f);
		
		// Update to main file
		try {
			JSONObject jsCurrentData = new JSONObject(currentData);
			Iterator<String> addresses = data.keys();
			while (addresses.hasNext()) {
				String address = (String) addresses.next();
				if(!jsCurrentData.has(address)){
					jsCurrentData.put(address, data.getJSONObject(address));
				}
				else{
					JSONObject jsMessOfAddress = data.getJSONObject(address);
					JSONObject jsCurrentMessOfAddress = jsCurrentData.getJSONObject(address);
					Iterator<String> ids = jsMessOfAddress.keys();
					while (ids.hasNext()) {
						String id = (String) ids.next();
						if(!jsCurrentMessOfAddress.has(id)){
							jsCurrentMessOfAddress.accumulate(id, jsMessOfAddress.getJSONObject(id));
						}
					}
				}
			}
			write(jsCurrentData.toString(), fileMain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void write(String data, File file){
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			os.write(data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(os != null)
					os.close();
			} catch (Exception e2) {
			}
		}
	}
	
	private String read(File file){
		BufferedReader br = null;
		InputStream is = null;
		InputStreamReader isr = null;
		StringBuilder str = new StringBuilder();
		try {
			is = new FileInputStream(file);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				str.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
				try {
					if(br != null)
						br.close();
					if(is != null)
						is.close();
					if(isr != null)
						isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return str.toString();
	}
}
