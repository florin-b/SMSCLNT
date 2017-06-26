package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

	public static boolean isTimeToSendSmsAlert() {
		Date dateNow = new Date();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 07);
		cal.set(Calendar.MINUTE, 30);

		Date dateMin = cal.getTime();

		return dateNow.after(dateMin);
	}

	public static String addDaysToDate(int nrDays) {

		DateFormat df = new SimpleDateFormat("yyyyMMdd");

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		cal.add(Calendar.DATE, nrDays);

		return df.format(cal.getTime());
	}

	public static String formatDateLocal(String stringDate) {
		String formattedDate;

		if (stringDate.contains("null"))
			return "";

		if (stringDate != null && stringDate.trim().length() == 0)
			return "";

		String pattern = "yyyyMMdd HHmmss";
		SimpleDateFormat formatInit = new SimpleDateFormat(pattern, Locale.US);
		SimpleDateFormat formatFinal = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", new Locale("en"));

		try {

			Date date = formatInit.parse(stringDate);

			formattedDate = formatFinal.format(date);

		} catch (ParseException e) {
			formattedDate = formatFinal.format(new Date());
		}

		return formattedDate;
	}

	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", new Locale("en"));
		return dateFormat.format(new Date());
	}

	public static int dateDiffInMinutes(String dateStart, String dateStop) {

		int result = 0;

		if (dateStart.length() == 0 || dateStop.length() == 0)
			return result;

		try {

			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh:mm:ss", new Locale("en"));

			Date d1 = dateFormat.parse(dateStart);
			Date d2 = dateFormat.parse(dateStop);

			long diff = d2.getTime() - d1.getTime();

			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			if (diffDays > 0) {
				diffMinutes += 1440 * diffDays;
			}

			if (diffHours > 0) {
				diffMinutes += 60 * diffHours;

			}

			if (diffMinutes > 0) {

			} else {
				if (diffMinutes != 0) {
					diffMinutes = 60 - Math.abs(diffMinutes);
				}
			}

			result = (int) diffMinutes;

		} catch (Exception e) {
			MailOperations.sendMail(e.toString());

		}

		return result;

	}

}
