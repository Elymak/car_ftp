package bc_om.car_ftp.utils;

import java.util.Date;

public class DateParser {
	
	public static String getDateFormat(long time) {
		Date now = new Date(time);
		String datetimeStr = now.toString();
		String res = datetimeStr.substring(4, 7) + " " + datetimeStr.substring(8,10) + " " + datetimeStr.substring(datetimeStr.length() - 4, datetimeStr.length());
		return res;
	}
	
}
