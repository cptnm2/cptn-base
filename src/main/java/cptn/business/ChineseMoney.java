package cptn.business;

import java.math.BigDecimal;

import cptn.util.MathUtil;

/**
 * 中文金钱的运算工具
 * 
 * @author cptn
 *
 */
public class ChineseMoney {
	private static String[] chineseDigits = { 
		"零", "壹", "贰", "叁", "肆",
		"伍", "陆", "柒", "捌", "玖"
	};

	public static String toChinese(int amount) {
		return toChinese(new BigDecimal(amount));
	}

	public static String toChinese(float amount) {
		return toChinese(new BigDecimal(amount));
	}

	public static String toChinese(double amount) {
		return toChinese(new BigDecimal(amount));
	}

	public static String toChinese(BigDecimal money) {
		if (money == null) {
			return "";
		}
		
		boolean negative = (money.compareTo(BigDecimal.ZERO) < 0);
		BigDecimal amount = money.abs().multiply(new BigDecimal(100));
		
		String val = MathUtil.format(amount, 0);
		
		long x = Long.parseLong(val);
		
		int numFen = (int) (x % 10);
		x /= 10;
		int numJiao = (int) (x % 10);
		x /= 10;
		
		int[] parts = new int[20];
		int numParts = 0;
		
		long temp = x;
		for (int i = 0; temp != 0; temp /= 10000, ++i) {
			parts[i] = (int) (temp % 10000);
			++numParts;
		}
		
		boolean beforeWanIsZero = true;
		String chineseStr = "";
		for (int j = 0; j < numParts; ++j) {
			String partChinese = partTranslate(parts[j]);
			if (j % 2 == 0) {
				beforeWanIsZero = "".equals(partChinese);
			}
			if (j != 0) {
				if (j % 2 == 0) {
					chineseStr = "亿" + chineseStr;
				} else if ("".equals(partChinese) && !beforeWanIsZero) {
					chineseStr = "零" + chineseStr;
				} else {
					if (parts[j - 1] < 1000 && parts[j - 1] > 0) {
						chineseStr = "零" + chineseStr;
					}
					chineseStr = "万" + chineseStr;
				}
			}
			chineseStr = partChinese + chineseStr;
		}
		
		if ("".equals(chineseStr)) {
			chineseStr = chineseDigits[0];
		} else if (negative) {
			chineseStr = "负" + chineseStr;
		}
		
		chineseStr += "元";
		
		if (numFen == 0 && numJiao == 0) {
			chineseStr += "整";
		} else if (numFen == 0) {
			chineseStr += chineseDigits[numJiao] + "角";
		} else if (numJiao == 0) {
			chineseStr += "零" + chineseDigits[numFen] + "分";
		} else {
			chineseStr += chineseDigits[numJiao] + "角"
					+ chineseDigits[numFen] + "分";
		}
		return chineseStr;
	}

	private static String partTranslate(int amountPart) {
		if (amountPart < 0 || amountPart > 10000) {
			throw new IllegalArgumentException(
					"参数必须是大于等于 0，小于 10000 的整数！");
		}
		String[] units = { "", "拾", "佰", "仟" };
		int temp = amountPart;
		String amountStr = new Integer(amountPart).toString();
		int amountStrLength = amountStr.length();
		boolean lastIsZero = true;
		String chineseStr = "";
		for (int i = 0; i < amountStrLength && temp != 0; temp /= 10, ++i) {
			int digit = temp % 10;
			if (digit == 0) {
				if (!lastIsZero) {
					chineseStr = "零" + chineseStr;
				}
				lastIsZero = true;
			} else {
				chineseStr = chineseDigits[digit]
						+ units[i] + chineseStr;
				lastIsZero = false;
			}
		}
		return chineseStr;
	}
}
