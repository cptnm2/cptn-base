package cptn.business;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 资金计算工具
 * @author cptn
 *
 */
public class MoneyUtil {
	/**
	 * 转换金额（保留两位小数，四舍五入）
	 * @param b
	 * @return
	 */
	public static BigDecimal toMoney(BigDecimal b) {
		if (b == null) {
			return BigDecimal.ZERO;
		}
		
		return b.setScale(2, RoundingMode.HALF_UP);
	}
	
	/**
	 * 计算乘积（保留两位小数，四舍五入）
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
		if (b1 == null || b2 == null) {
			return BigDecimal.ZERO;
		}
		
		return toMoney(b1.multiply(b2));
	}
	
	/**
	 * 计算商（保留两位小数，四舍五入）
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal divide(BigDecimal b1, BigDecimal b2) {
		if (b1 == null || b2 == null) {
			return BigDecimal.ZERO;
		}
		
		return toMoney(b1.divide(b2));
	}
	
	/**
	 * 判断金额是否相等(保留两位小数，四舍五入)
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean isEqual(BigDecimal b1, BigDecimal b2) {
		if (b1 == null && b2 == null) {
			return true;
		}
		else if (b1 == null || b2 == null) {
			return false;
		}
		else {
			return (toMoney(b1).compareTo(toMoney(b2)) == 0);
		}
	}
}
