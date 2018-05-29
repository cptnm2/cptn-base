package cptn.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil {
	
	/**
	 * yyyy-MM-dd H:mm:ss
	 */
	public static final String DTP_LONG = "yyyy-MM-dd H:mm:ss";
	
	/**
	 * yyyy-M-d H:m:s
	 */
	public static final String DTP_SHORT = "yyyy-M-d H:m:s";

	/**
	 * yyyy-MM-dd
	 */
	public static final String DP_LONG = "yyyy-MM-dd";
	
	/**
	 * yyyy-M-d
	 */
	public static final String DP_SHORT = "yyyy-M-d";
	
	/**
	 * H:mm:ss
	 */
	public static final String TP_LONG = "H:mm:ss";
	
	/**
	 * H:m:s
	 */
	public static final String TP_SHORT = "H:m:s";
	
	private static Logger log = LoggerFactory.getLogger(DateUtil.class);
	
	public static Date getCurrentTime() {
		return new Date(System.currentTimeMillis());
	}
	
	public static String getCurDateStr(String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(getCurrentTime());
	}
	
	public static String getDateStr(Date date) {
		return getDateStr(date, DP_LONG);
	}
	
	public static String getDateStr(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	/**
	 * 判断时间是否过期
	 * @param time
	 * @return
	 */
	public static boolean isExpired(String time, String pattern) {
		boolean result = true;
		
		try {
			Date cur = DateUtil.getCurrentTime();
			SimpleDateFormat df = new SimpleDateFormat(pattern); 
			Date dest = df.parse(time);
			
			if (dest.after(cur)) { // 过期时间大于当前时间，未过期
				result = false;
			}
			
		} catch (ParseException ex) {
			log.warn("", ex);
		}
		
		return result;
	}
	
	/**
	 * 判断时间是否已过期，单位：毫秒
	 * @param expireTime
	 * @return
	 */
	public static boolean isExpired(long expireTime) {
		if (expireTime == 0) {
			return false;
		}
		
		return expireTime <= System.currentTimeMillis();
	}
	
	/**
	 * 解析日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		if (StrUtil.INSTANCE.isEmpty2(date)) {
			return null;
		}

		try {
			DateFormat df = new SimpleDateFormat(pattern);
			return df.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 解析日期（yyyy-MM-dd 或  yyyy-M-d）
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		if (StrUtil.INSTANCE.isEmpty2(date)) {
			return null;
		}
		
		String pattern = (date.length() == 10? DP_LONG: DP_SHORT);
		return parse(date, pattern);
	}
}
