package dinhtrong.app.backupsms.util;

import java.util.Locale;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;


public class Utils {
	public static String formatPhoneNumberShow(String phoneNumber){
		try {
			PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
			PhoneNumber phone = phoneUtil.parse(phoneNumber, Locale.getDefault().getCountry());
			phoneNumber = phoneUtil.format(phone, PhoneNumberFormat.NATIONAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return phoneNumber;
	}
}
