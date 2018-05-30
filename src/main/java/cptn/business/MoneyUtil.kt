package cptn.business

import java.math.BigDecimal
import java.math.RoundingMode

// 资金计算工具
object MoneyUtil {
    /**
     * 转换金额（保留两位小数，四舍五入）
     * @param b
     * @return
     */
    fun toMoney(b: BigDecimal?): BigDecimal {
        return if (b == null) {
            BigDecimal.ZERO
        } else b.setScale(2, RoundingMode.HALF_UP)

    }

    /**
     * 计算乘积（保留两位小数，四舍五入）
     * @param b1
     * @param b2
     * @return
     */
    fun multiply(b1: BigDecimal?, b2: BigDecimal?): BigDecimal {
        return if (b1 == null || b2 == null) {
            BigDecimal.ZERO
        } else toMoney(b1.multiply(b2))

    }

    /**
     * 计算商（保留两位小数，四舍五入）
     * @param b1
     * @param b2
     * @return
     */
    fun divide(b1: BigDecimal?, b2: BigDecimal?): BigDecimal {
        return if (b1 == null || b2 == null) {
            BigDecimal.ZERO
        } else toMoney(b1.divide(b2))

    }

    /**
     * 判断金额是否相等(保留两位小数，四舍五入)
     * @param b1
     * @param b2
     * @return
     */
    fun isEqual(b1: BigDecimal?, b2: BigDecimal?): Boolean {
        return if (b1 == null && b2 == null) {
            true
        } else if (b1 == null || b2 == null) {
            false
        } else {
            toMoney(b1).compareTo(toMoney(b2)) == 0
        }
    }
}
