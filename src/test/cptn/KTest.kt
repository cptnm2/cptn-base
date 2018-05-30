package cptn

import cptn.business.ChineseMoney
import org.junit.Assert.*
import org.junit.Test
import java.io.UnsupportedEncodingException

import cptn.util.StrUtil


open class Base() {
    init {
        // println("Init Base")
    }
}


class KTest() : Base() {
    init {
        // println("Init KTest")
    }

    @Test
    fun test() {
    }

    fun testLoop() {
        loop@ for (i in 1..6) {
            for (j in 1..6) {
                println("$i, $j")
                if (j == 5) {
                    break@loop
                }
            }
        }
    }

    fun testHexStr() {
        val data = byteArrayOf(1, 2, 10, 22)
        println(StrUtil.byteArrayToHexStr(data))
    }

    @Test
    fun testDecimal() {
        var value = "10101"
        assertTrue(StrUtil.safeParseDecimal(value).toInt() == 10101)

        value = "-1"
        assertTrue(StrUtil.safeParseDecimal(value).toInt() == -1)

        value = "dna"
        assertTrue(StrUtil.safeParseDecimal(value).toInt() == 0)
    }

    @Test
    @Throws(UnsupportedEncodingException::class)
    fun testDecodeUtf() {
        val src = "%E8%B5%B5"
        val dst = StrUtil.ansiToUtf8(src);
        println(dst)
    }

    @Test
    fun testEmpty() {
        assertTrue(StrUtil.isEmpty(null))
        assertTrue(StrUtil.isEmpty(""))
        assertTrue(!StrUtil.isEmpty("   "))
        assertTrue(!StrUtil.isEmpty("ABCD"))

        assertTrue(StrUtil.isEmpty2(null))
        assertTrue(StrUtil.isEmpty2(""))
        assertTrue(StrUtil.isEmpty2("   "))
        assertTrue(!StrUtil.isEmpty2("ABCD"))
    }

    @Test
    fun testTrim() {
        assertEquals(StrUtil.safeTrim(null), null)
        assertEquals(StrUtil.safeTrim(""), "")
        assertEquals(StrUtil.safeTrim("  "), "")
        assertEquals(StrUtil.safeTrim(" AAA "), "AAA")

        assertEquals(StrUtil.safeTrim2(null), "")
        assertEquals(StrUtil.safeTrim2(""), "")
        assertEquals(StrUtil.safeTrim2("  "), "")
        assertEquals(StrUtil.safeTrim2(" AAA "), "AAA")
    }

    @Test
    fun testParseInt() {
        assertEquals(StrUtil.safeParseInt(null, 0), 0)
        assertEquals(StrUtil.safeParseInt("", 0), 0)
        assertEquals(StrUtil.safeParseInt("abc", 0), 0)
        assertEquals(StrUtil.safeParseInt("123.4", 0), 0)
        assertEquals(StrUtil.safeParseInt("123", 0), 123)
    }
}
