package dinhtrong.app.backupsms;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable{
	private int id, type, totals;
	private String body, date, address;
	public Message(int id, String body, String date, String address, int type){
		this.id = id;
		this.address = address;
		this.body = body;
		this.date = date;
		this.type = type;
	}
	
	public Message(JSONObject json){
		this.id = json.optInt("id");
		this.address = json.optString("address");
		this.body = json.optString("body");
		this.date = json.optString("date");
		this.type = json.optInt("type");
	}
	
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		try{
			json.put("id", id);
			json.put("body", body);
			json.put("address", address);
			json.put("date", date);
			json.put("type", type);
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
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(address);
		dest.writeString(body);
		dest.writeString(date);
		dest.writeInt(type);
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
