package utils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

	public static boolean isTimeToSendSmsAlert() {
		Date dateNow = new Date();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 07);
		cal.set(Calendar.MINUTE, 30);

		Date dateMin = cal.getTime();

		return dateNow.after(dateMin);
	}

	
}
