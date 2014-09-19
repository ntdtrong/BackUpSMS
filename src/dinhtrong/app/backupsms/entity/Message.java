package dinhtrong.app.backupsms.entity;

import org.json.JSONObject;

import dinhtrong.app.backupsms.util.Constants;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable{
	private int id, type, totals, contactId;
	private String body, date, address;
	public Message(int id, String body, String date, String address, int type, int contactId){
		this.id = id;
		this.address = address;
		this.body = body;
		this.date = date;
		this.type = type;
		this.contactId = contactId;
	}
	
	public Message(JSONObject json){
		this.id = json.optInt("id");
		this.address = json.optString("address");
		this.body = json.optString("body");
		this.date = json.optString("date");
		this.type = json.optInt("type");
		this.contactId = json.optInt("contact_id");
	}
	
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		try{
			json.put("id", id);
			json.put("body", body);
			json.put("address", address);
			json.put("date", date);
			json.put("type", type);
			json.put("contact_id", contactId);
			json.put("data_type", Constants.DATA_TYPE_MESSAGE);
		}
		catch(Exception e){}
		return json;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	public Message(Parcel in){
		id = in.readInt();
		address = in.readString();
		body = in.readString();
		date = in.readString();
		type = in.readInt();
		contactId = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(address);
		dest.writeString(body);
		dest.writeString(date);
		dest.writeInt(type);
		dest.writeInt(contactId);
	}	
	
	public static final Parcelable.Creator<Message> CREATOR = new Creator<Message>() {
	    public Message createFromParcel(Parcel source) {
	        return new Message(source);
	    }
	    public Message[] newArray(int size) {
	        return new Message[size];
	    }
	};
}
