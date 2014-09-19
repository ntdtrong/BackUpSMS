package dinhtrong.app.backupsms.util;

import java.util.Comparator;

import dinhtrong.app.backupsms.entity.Message;

public class CustomCompare implements Comparator<Message>{

	@Override
	public int compare(Message m1, Message m2) {
		return m1.getId()-m2.getId();
	}

}
