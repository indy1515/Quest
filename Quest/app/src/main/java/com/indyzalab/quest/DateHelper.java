package com.indyzalab.quest;

import java.util.Date;

public class DateHelper {
	
	public static Date timestampToDate(String timestamp){
		
		//Added time for GMT+7 detail http://www.epochconverter.com/epoch/timezones.php?epoch=1408399890
		Date expiry = new Date((Long.parseLong(timestamp)) * 1000);
		return expiry;
		
	}
	
	public static String getNowTimestamp(){
		/**
		 * Get current timestamp
		 */
		long time =  (System.currentTimeMillis()/1000L);
		return ""+time;
	}
}
