package dinhtrong.app.backupsms;

import java.util.Comparator;

public class CustomCompare implements Comparator<Message>{

	@Override
	public int compare(Message m1, Message m2) {
		return m1.getId()-m2.getId();
	}

}
