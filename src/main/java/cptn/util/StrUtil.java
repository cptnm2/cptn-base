package cptn.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 字符串工具类
 * @author cptn
 * 
 */
public class StrUtil {

	/**
	 * Determine if a string is empty (null or "")
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return (null == value || "".equals(value));
	}

	/**
	 * Determine if a string is empty (null or "" or "   ")
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty2(String value) {
		if (value != null) {
			value = value.trim();
		}

		return (null == value || "".equals(value));
	}

	/**
	 * Safely trim, return null when value is null
	 * 
	 * @param value
	 * @return
	 */
	public static String safeTrim(String value) {
		if (value == null) {
			return null;
		}

		return value.trim();
	}

	/**
	 * Safely trim, return "" when value is null
	 * 
	 * @param value
	 * @return
	 */
	public static String safeTrim2(String value) {
		if (value == null) {
			return "";
		}

		return value.trim();
	}

	/**
	 * 判断两个字符串是否相等(区分大小写)
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEqual(String a, String b) {
		if (a == null && b == null) {
			return true;
		}

		if (a == null && b != null) {
			return false;
		}

		if (a != null && b == null) {
			return false;
		}

		return a.equals(b);
	}

	/**
	 * 判断两个字符串是否相等(不分大小写)
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEqualIgnoreCase(String a, String b) {
		if (a == null && b == null) {
			return true;
		}

		if (a == null && b != null) {
			return false;
		}

		if (a != null && b == null) {
			return false;
		}

		return a.equalsIgnoreCase(b);
	}

	/**
	 * Safely parse an integer, return 0 when exception occurs
	 * 
	 * @param value
	 * @return
	 */
	public static int safeParseInt(String value) {
		return safeParseInt(value, 0);
	}

	/**
	 * Safely parse an integer, return defValue when exception occurs
	 * 
	 * @param value
	 * @return
	 */
	public static int safeParseInt(String value, int defValue) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * Safely parse a decimal, return 0 when exception occurs
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal safeParseDecimal(String value) {
		return safeParseDecimal(value, BigDecimal.ZERO);
	}

	/**
	 * Safely parse a decimal, return defValue when exception occurs
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal safeParseDecimal(String value, BigDecimal defValue) {
		try {
			return new BigDecimal(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * Generate a UUID with 36 chars, including 4 "-"
	 * 
	 * @return
	 */
	public static String genUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Generate a UUID with 32 chars, without "-"
	 * 
	 * @return
	 */
	public static String genUUID32() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * Change first char to upper
	 * 
	 * @param value
	 * @return
	 */
	public static String firstCharUpper(String value) {
		if (isEmpty(value)) {
			return "";
		}

		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}

	/**
	 * Determine if a char is a Chinese char
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * Determine if a string contains Chinese chars
	 * 
	 * @param s
	 * @return
	 */
	public static boolean containsChinese(String s) {
		if (s == null) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			if (isChinese(s.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Truncate the head of a string, which Chinese chars take 2 chars place
	 * 
	 * @param src
	 * @param maxLength
	 * @return
	 */
	public static String truncHead(String src, int maxLength) {
		if (isEmpty(src) || maxLength <= 0) {
			return "";
		}

		int len;
		int totalLen = 0;
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);

			if (isChinese(c)) {
				len = 2;
			} else {
				len = 1;
			}

			totalLen += len;

			if (totalLen > maxLength) {
				return src.substring(0, i);
			} else if (totalLen == maxLength) {
				return src.substring(0, i + 1);
			}
		}

		return src;
	}

	/**
	 * 截取字符串头部指定长度的子字符串, 中文长度X2, 如果字符串被截断, 自动加上后缀
	 * 
	 * @param src
	 * @param maxLength
	 * @param suffix
	 * @return
	 */
	public static String truncHead2(String src, int maxLength, String suffix) {
		String dst = truncHead(src, maxLength);

		if (dst.length() < src.length()) {
			dst += suffix;
		}

		return dst;
	}

	/**
	 * Ascii to UTF-8
	 * 
	 * @param param
	 * @return
	 */
	public static String ansiToUtf8(String param) {
		if (isEmpty(param)) {
			return "";
		}

		try {
			return new String(param.getBytes("ISO8859_1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}

		return "";
	}

	/**
	 * UTF-8 to Ascii
	 * 
	 * @param param
	 * @return
	 */
	public static String utf8ToAnsi(String param) {
		if (isEmpty(param)) {
			return "";
		}

		try {
			return new String(param.getBytes("UTF-8"), "ISO8859_1");
		} catch (UnsupportedEncodingException e) {
		}

		return "";
	}

	public static String ansiToGBK(String param) {
		if (isEmpty(param)) {
			return "";
		}

		try {
			return new String(param.getBytes("ISO8859_1"), "GBK");
		} catch (UnsupportedEncodingException e) {
		}

		return "";
	}

	/**
	 * Split a string, and safely trim each element
	 * 
	 * @param src
	 * @param delimeter
	 * @return
	 */
	public static String[] safeSplit(String src, String delimeter) {
		if (isEmpty2(src)) {
			return new String[] { "" };
		}

		String[] data = src.split(delimeter);

		for (int i = 0; i < data.length; i++) {
			data[i] = data[i].trim();
		}

		return data;
	}

	/**
	 * Get system default CRLF
	 * 
	 * @return
	 */
	public static String getFileSep() {
		return System.getProperty("file.separator");
	}

	/**
	 * Truncate the head of a string, and will append "..." at the end if the
	 * length is overflow
	 * 
	 * @param src
	 * @param maxCharLen
	 * @return
	 */
	public static String getHead(String src, int maxCharLen) {
		String title = src;
		int len = 0;

		for (int i = 0; i < title.length(); i++) {
			if (StrUtil.isChinese(title.charAt(i))) {
				len += 2;
			} else {
				len++;
			}

			if (len > maxCharLen) {
				return (title.substring(0, i) + "...");
			} else if (len == maxCharLen) {
				if (i < (src.length() - 1)) {
					// 长度相等且不是结�?
					return (title.substring(0, i + 1) + "...");
				}
			}
		}

		return title;
	}

	/**
	 * Determine if an integer exists in an array
	 * 
	 * @param arrs
	 * @param key
	 * @return
	 */
	public static boolean intInArr(int[] arrs, int key) {
		if (arrs == null || arrs.length == 0) {
			return false;
		}

		for (Integer i : arrs) {
			if (i == key) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generate random string
	 * 
	 * @param chars
	 * @param len
	 * @return
	 */
	public static String genRandomStr(String chars, int len) {
		StringBuffer buff = new StringBuffer();
		Random ran = new Random();

		for (int i = 0; i < len; i++) {
			int pos = ran.nextInt(chars.length());
			buff.append(chars.charAt(pos));
		}

		return buff.toString();
	}

	/**
	 * Concat multiple strings with sep
	 * 
	 * @param list
	 * @param sep
	 * @return
	 */
	public static String joinStrs(List<String> list, String sep, String leftQuote, String rightQuote) {
		StringBuffer buff = new StringBuffer();

		boolean left = (!StrUtil.isEmpty(leftQuote));
		boolean right = (!StrUtil.isEmpty(rightQuote));

		for (String s : list) {
			if (buff.length() > 0) {
				buff.append(sep);
			}

			if (left) {
				buff.append(leftQuote);
			}

			buff.append(s);

			if (right) {
				buff.append(rightQuote);
			}
		}

		return buff.toString();
	}

	/**
	 * Concat multiple strings with sep
	 * 
	 * @param list
	 * @param sep
	 * @return
	 */
	public static String joinStrs(List<String> list, String sep) {
		return joinStrs(list, sep, null, null);
	}

	/**
	 * 格式化金额
	 * 
	 * @param val
	 * @return
	 */
	public static String formatMoney(BigDecimal val) {
		return formatNumber(val, "###0.00");
	}

	/**
	 * 格式化实数
	 * 
	 * @param val
	 * @param digit
	 * @return
	 */
	public static String formatNumber(float val, int digit) {
		return formatNumber(new BigDecimal(val), digit);
	}

	/**
	 * 格式化实数
	 * 
	 * @param val
	 * @param digit
	 * @return
	 */
	public static String formatNumber(double val, int digit) {
		return formatNumber(new BigDecimal(val), digit);
	}

	/**
	 * 格式化实数
	 * 
	 * @param val
	 * @param digit
	 * @return
	 */
	public static String formatNumber(BigDecimal val, int digit) {
		StringBuffer buff = new StringBuffer();

		buff.append("###0");

		if (digit > 0) {
			buff.append(".");

			for (int i = 0; i < digit; i++) {
				buff.append("0");
			}
		}

		return formatNumber(val, buff.toString());
	}

	/**
	 * 格式化实数
	 * 
	 * @param val
	 * @param pattern
	 * @return
	 */
	public static String formatNumber(BigDecimal val, String pattern) {
		DecimalFormat nf = (DecimalFormat) NumberFormat.getNumberInstance();
		nf.applyPattern(pattern);
		return nf.format(val);
	}

	/**
	 * 字节数组转换成十六进制字符串
	 * 
	 * @param buf
	 * @return
	 */
	public static String byteArrayToHexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex.toLowerCase());
		}

		return sb.toString();
	}

	/**
	 * 十六进制字符串转换成字节数组
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] hexStrToByteArray(String hexStr) {
		if (isEmpty(hexStr)) {
			return null;
		}

		byte[] result = new byte[hexStr.length() / 2];

		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			
			result[i] = (byte) (high * 16 + low);
		}
		
		return result;
	}

	/**
	 * 将字符串中间变成星号，比如银行卡号 变成 6225********2345
	 * @param frontLen 前端长度
	 * @param rearLen 后端长度
	 * @return
	 */
	public static String hideMidStr(String src, int frontLen, int rearLen) {
		if (frontLen < 0 || rearLen < 0) {
			return src;
		}
		
		if (StrUtil.isEmpty(src)) {
			return src;
		}
		
		int len = src.length();
		
		if (len <= (frontLen + rearLen)) {
			return src;
		}
		else {
			StringBuffer buff = new StringBuffer(src.substring(0, frontLen));
			
			for (int i = 0; i < len - frontLen - rearLen; i++) {
				buff.append("*");
			}
			
			buff.append(src.substring(len - rearLen));
			
			return buff.toString();
		}
	}
	
	/**
	 * 判断密码强度
	 * @param password
	 * @param caseSensitive 是否区分大小写
	 * @return 1：低，2：中，3：高，4：完美
	 */
	public static int getPasswordLevel(String password, boolean caseSensitive) {
		char ch;
		boolean hasUpper = false; // 包含大写字母
		boolean hasLower = false; // 包含小写字母
		boolean hasSymbol = false; // 包含符号
		boolean hasNumber = false; // 包含数字

		if (StrUtil.isEmpty2(password)) {
			return 1;
		}
		
		if (!caseSensitive) { // 如果大小写不敏感，全部转换成大写，最多只会返回3
			password = password.toUpperCase();
		}
		
		for (int i = 0; i < password.length(); i++) {
			ch = password.charAt(i);
			
			if (ch >= 'A' && ch <= 'Z') {
				hasUpper = true;
			}
			else if (ch >= 'a' && ch <= 'z') {
				hasLower = true;
			}
			else if (ch >= '0' && ch <= '9') {
				hasNumber = true;
			}
			else { // 数字、字母以外的都算符号
				hasSymbol = true;
			}
		}
		
		// 判断强度
		int count = 0;
		
		if (hasUpper) {
			count++;
		}
		if (hasLower) {
			count++;
		}
		if (hasSymbol) {
			count++;
		}
		if (hasNumber) {
			count++;
		}
		
		int result = count > 1 ? count : 1;
		
		return result;
	}

	/**
	 * 把参数连接成一个字符串，并在中间加上下划线(空指针会输出null)
	 * 主要用于创建一个特有的字符串，比如缓存的key
	 * @param args
	 * @return
	 */
	public static String genQueryKey(Object... args) {
		StringBuffer buff = new StringBuffer();
		for (Object obj : args) {
			buff.append("_");

			if (obj == null) {
				buff.append("null");
			} else {
				buff.append(obj.toString());
			}
		}

		return buff.toString().replaceAll(" ", "_");
	}
}
