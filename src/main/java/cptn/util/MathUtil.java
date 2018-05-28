package cptn.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathUtil {
	/**
	 * 取得浮点格式，小数点后指定位数
	 * @param n
	 * @return
	 */
	public static String getDecimalPattern(int n) {
		String pattern;
		
		if (n == 0) {
			pattern = "###0";
		}
		else if (n == 1) {
			pattern = "###0.0";
		}
		else if (n == 2) {
			pattern = "###0.00";
		}
		else if (n == 3) {
			pattern = "###0.000";
		}
		else if (n == 4) {
			pattern = "###0.0000";
		}
		else {
			pattern = "###0";
		}
		
		return pattern;
	}
	
	// 格式化常规浮点数
	/**
	 * 格式化
	 * @param qty
	 * @param digits
	 * @return
	 */
	public static String format(int qty, int digits) {
		DecimalFormat nf = (DecimalFormat) NumberFormat.getNumberInstance();
		nf.applyPattern(getDecimalPattern(digits));
		return nf.format(qty);
	}

	/**
	 * 格式化
	 * @param qty
	 * @param digits
	 * @return
	 */
	public static String format(float qty, int digits) {
		DecimalFormat nf = (DecimalFormat) NumberFormat.getNumberInstance();
		nf.applyPattern(getDecimalPattern(digits));
		return nf.format(qty);
	}

	/**
	 * 格式化
	 * @param qty
	 * @param digits
	 * @return
	 */
	public static String format(double qty, int digits) {
		DecimalFormat nf = (DecimalFormat) NumberFormat.getNumberInstance();
		nf.applyPattern(getDecimalPattern(digits));
		return nf.format(qty);
	}

	/**
	 * 格式化
	 * @param qty
	 * @param digits
	 * @return
	 */
	public static String format(BigDecimal qty, int digits) {
		DecimalFormat nf = (DecimalFormat) NumberFormat.getNumberInstance();
		nf.applyPattern(getDecimalPattern(digits));
		return nf.format(qty);
	}
	
	/**
	 * 整型转换成长整型
	 * @param value
	 * @return
	 */
	public static int toInt(long value) {
		return ((Long) value).intValue();
	}
	
	/**
	 * 长整型转换成整型
	 * @param value
	 * @return
	 */
	public static long toLong(int value) {
		return ((long) value);
	}
	
	/**
	 * 随机从队列中抽取一组值
	 * 
	 * @param length
	 * @param count
	 * @return 列表中的值代表一个队列中的索引，而不是实际值
	 */
	public static List<Integer> randomPick(int length, int count) {
		List<Integer> list = new ArrayList<Integer>();
		
		if (length <= 0 || count <= 0) {
			return list;
		}
		
		int[] ids = new int[length];
		for (int i = 0; i < length; i++) {
			ids[i] = i;
		}
		
		Random rnd = new Random();
		int x;
		
		for (int n = length - 1; n >= 0; n--) {
			x = rnd.nextInt(n + 1);
			list.add(ids[x]);
			
			ids[x] = ids[n]; // 此时 x 位置的数字已用掉，故将尾部未使用的数据移过来
			
			if (--count == 0) {
				break;
			}
		}
		
		return list;
	}
}
