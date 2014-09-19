package dinhtrong.app.backupsms.entity;

import org.json.JSONObject;

import dinhtrong.app.backupsms.util.Constants;

import android.util.Log;

public class Contact {
	private int id;
	private String phoneNumber, email, fullname;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void show(){
		Log.e("contact", "id : " + id + ", name: " + fullname +", phone: " + phoneNumber + ", email : " + email);
	}
	
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		try{
			json.put("id", id);
			json.put("body", fullname);
			json.put("address", phoneNumber);
			json.put("date", email);
			json.put("data_type", Constants.DATA_TYPE_CONTACT);
		}
		catch(Exception e){}
		return json;
	}
	
}
