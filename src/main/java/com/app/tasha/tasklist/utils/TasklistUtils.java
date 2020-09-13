package com.app.tasha.tasklist.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TasklistUtils {

	public static String formatInsertDate(Date insertDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(TasklistConstants.DATE_FORMAT);
		String formattedDate = sdf.format(insertDate);
		return formattedDate;
	}
	
	public static Date parseInsertDate(String insertDate) {
		Date parsedDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(TasklistConstants.DATE_FORMAT);
			parsedDate = (Date) sdf.parse(insertDate);
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}

}
