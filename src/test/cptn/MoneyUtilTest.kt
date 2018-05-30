package cptn

import cptn.business.ChineseMoney
import cptn.business.MoneyUtil
import org.junit.Test

import java.math.BigDecimal

import org.junit.Assert.assertTrue

class MoneyUtilTest {
    @Test
    fun testDecimal() {
        assertTrue(MoneyUtil.isEqual(null, null))
        assertTrue(MoneyUtil.isEqual(BigDecimal("1.15"), BigDecimal("1.15")))
        assertTrue(MoneyUtil.isEqual(BigDecimal("1.15"), BigDecimal("1.1500")))
        assertTrue(MoneyUtil.isEqual(BigDecimal("1.15"), BigDecimal("1.151")))


        assertTrue(!MoneyUtil.isEqual(null, BigDecimal("1.15")))
        assertTrue(!MoneyUtil.isEqual(BigDecimal("1.15"), null))
        assertTrue(!MoneyUtil.isEqual(BigDecimal("1.155"), BigDecimal("1.15")))
    }

    @Test
    fun testChinese() {
        println("转换中文金额");
        println("-------------------------");
        println("25000000000005.999: " + ChineseMoney.toChinese(25000000000005.999));
        println("45689263.626: " + ChineseMoney.toChinese(45689263.626));
        println("0.69457: " + ChineseMoney.toChinese(0.69457));
        println("250.0: " + ChineseMoney.toChinese(250.0));
        println("0: " + ChineseMoney.toChinese(0));
        println("-------------------------");
    }
}
