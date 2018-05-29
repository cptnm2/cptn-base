@file:JvmName("StrUtil")

package cptn.util

import java.io.UnsupportedEncodingException
import java.math.BigDecimal
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object StrUtil {
    // 系统换行符
    val fileSep: String
        get() = System.getProperty("file.separator")

    /**
     * Determine if a string is empty (null or "")
     *
     * @param value
     * @return
     */
    fun isEmpty(value: String?): Boolean {
        return null == value || "" == value
    }

    /**
     * Determine if a string is empty (null or "" or "   ")
     *
     * @param value
     * @return
     */
    fun isEmpty2(value: String?): Boolean {
        var s = value?.trim()
        return null == s || "" == s
    }

    /**
     * Safely trim, return null when value is null
     *
     * @param value
     * @return
     */
    fun safeTrim(value: String?): String? {
        return value?.trim { it <= ' ' }
    }

    /**
     * Safely trim, return "" when value is null
     *
     * @param value
     * @return
     */
    fun safeTrim2(value: String?): String {
        return if (value == null) {
            ""
        } else value.trim { it <= ' ' }
    }

    /**
     * 判断两个字符串是否相等(区分大小写)
     *
     * @param a
     * @param b
     * @return
     */
    fun isEqual(a: String?, b: String?): Boolean {
        if (a == null && b == null) {
            return true
        }

        if (a == null && b != null) {
            return false
        }

        return if (a != null && b == null) {
            false
        } else a == b
    }

    /**
     * 判断两个字符串是否相等(不分大小写)
     *
     * @param a
     * @param b
     * @return
     */
    fun isEqualIgnoreCase(a: String?, b: String?): Boolean {
        if (a == null && b == null) {
            return true
        }

        if (a == null && b != null) {
            return false
        }

        return if (a != null && b == null) {
            false
        } else a!!.equals(b!!, ignoreCase = true)
    }

    /**
     * Safely parse an integer, return defValue when exception occurs
     *
     * @param value
     * @return
     */
    @JvmOverloads
    fun safeParseInt(value: String, defValue: Int = 0): Int {
        try {
            return Integer.parseInt(value)
        } catch (e: Exception) {
            return defValue
        }
    }

    /**
     * Safely parse a decimal, return defValue when exception occurs
     *
     * @param value
     * @return
     */
    @JvmOverloads
    fun safeParseDecimal(value: String, defValue: BigDecimal = BigDecimal.ZERO): BigDecimal {
        try {
            return BigDecimal(value)
        } catch (e: Exception) {
            return defValue
        }
    }

    /**
     * Generate a UUID with 36 chars, including 4 "-"
     *
     * @return
     */
    fun genUUID(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * Generate a UUID with 32 chars, without "-"
     *
     * @return
     */
    fun genUUID32(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    /**
     * Change first char to upper
     *
     * @param value
     * @return
     */
    fun firstCharUpper(value: String): String {
        return if (isEmpty(value)) {
            ""
        } else value.substring(0, 1).toUpperCase() + value.substring(1)
    }

    /**
     * Determine if a char is a Chinese char
     *
     * @param c
     * @return
     */
    fun isChinese(c: Char): Boolean {
        val ub = Character.UnicodeBlock.of(c)

        return if (ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            true
        } else false
    }

    /**
     * Determine if a string contains Chinese chars
     *
     * @param s
     * @return
     */
    fun containsChinese(s: String?): Boolean {
        if (s == null) {
            return false
        }

        for (i in 0 until s.length) {
            if (isChinese(s[i])) {
                return true
            }
        }

        return false
    }

    /**
     * Truncate the head of a string, which Chinese chars take 2 chars place
     *
     * @param src
     * @param maxLength
     * @return
     */
    fun truncHead(src: String, maxLength: Int): String {
        if (isEmpty(src) || maxLength <= 0) {
            return ""
        }

        var len: Int
        var totalLen = 0
        for (i in 0 until src.length) {
            val c = src[i]

            if (isChinese(c)) {
                len = 2
            } else {
                len = 1
            }

            totalLen += len

            if (totalLen > maxLength) {
                return src.substring(0, i)
            } else if (totalLen == maxLength) {
                return src.substring(0, i + 1)
            }
        }

        return src
    }

    /**
     * 截取字符串头部指定长度的子字符串, 中文长度X2, 如果字符串被截断, 自动加上后缀
     *
     * @param src
     * @param maxLength
     * @param suffix
     * @return
     */
    fun truncHead2(src: String, maxLength: Int, suffix: String): String {
        var dst = truncHead(src, maxLength)

        if (dst.length < src.length) {
            dst += suffix
        }

        return dst
    }

    /**
     * Ascii to UTF-8
     *
     * @param param
     * @return
     */
    fun ansiToUtf8(param: String): String {
        if (isEmpty(param)) {
            return ""
        }

        try {
            return String(param.toByteArray(Charset.forName("ISO8859_1")), Charset.forName("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            println(e)
        }

        return ""
    }

    /**
     * UTF-8 to Ascii
     *
     * @param param
     * @return
     */
    fun utf8ToAnsi(param: String): String {
        if (isEmpty(param)) {
            return ""
        }

        try {
            return String(param.toByteArray(charset("UTF-8")), Charset.forName("ISO8859_1"))
        } catch (e: UnsupportedEncodingException) {
        }

        return ""
    }

    fun ansiToGBK(param: String): String {
        if (isEmpty(param)) {
            return ""
        }

        try {
            return String(param.toByteArray(charset("ISO8859_1")), Charset.forName("GBK"))
        } catch (e: UnsupportedEncodingException) {
        }

        return ""
    }

    /**
     * Split a string, and safely trim each element
     *
     * @param src
     * @param delimeter
     * @return
     */
    fun safeSplit(src: String, delimeter: String): Array<String> {
        if (isEmpty2(src)) {
            return arrayOf("")
        }

        val data = src.split(delimeter.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

        for (i in data.indices) {
            data[i] = data[i].trim({ it <= ' ' })
        }

        return data
    }

    /**
     * Truncate the head of a string, and will append "..." at the end if the
     * length is overflow
     *
     * @param src
     * @param maxCharLen
     * @return
     */
    fun getHead(src: String, maxCharLen: Int): String {
        var len = 0

        for (i in 0 until src.length) {
            if (isChinese(src[i])) {
                len += 2
            } else {
                len++
            }

            if (len > maxCharLen) {
                return src.substring(0, i) + "..."
            } else if (len == maxCharLen) {
                if (i < src.length - 1) {
                    // 长度相等且不是结�?
                    return src.substring(0, i + 1) + "..."
                }
            }
        }

        return src
    }

    /**
     * Determine if an integer exists in an array
     *
     * @param arrs
     * @param key
     * @return
     */
    fun intInArr(arrs: IntArray?, key: Int): Boolean {
        if (arrs == null || arrs.size == 0) {
            return false
        }

        for (i in arrs) {
            if (i == key) {
                return true
            }
        }
        return false
    }

    /**
     * Generate random string
     *
     * @param chars
     * @param len
     * @return
     */
    fun genRandomStr(chars: String, len: Int): String {
        val buff = StringBuffer()
        val ran = Random()

        for (i in 0 until len) {
            val pos = ran.nextInt(chars.length)
            buff.append(chars[pos])
        }

        return buff.toString()
    }

    /**
     * Concat multiple strings with sep
     *
     * @param list
     * @param sep
     * @return
     */
    fun joinStrs(list: List<String>, sep: String, leftQuote: String? = null, rightQuote: String? = null): String {
        val buff = StringBuffer()

        val left = !isEmpty(leftQuote)
        val right = !isEmpty(rightQuote)

        for (s in list) {
            if (buff.length > 0) {
                buff.append(sep)
            }

            if (left) {
                buff.append(leftQuote)
            }

            buff.append(s)

            if (right) {
                buff.append(rightQuote)
            }
        }

        return buff.toString()
    }

    /**
     * 格式化金额
     *
     * @param val
     * @return
     */
    fun formatMoney(`val`: BigDecimal): String {
        return formatNumber(`val`, "###0.00")
    }

    /**
     * 格式化实数
     *
     * @param val
     * @param digit
     * @return
     */
    fun formatNumber(`val`: Float, digit: Int): String {
        return formatNumber(BigDecimal(`val`.toDouble()), digit)
    }

    /**
     * 格式化实数
     *
     * @param val
     * @param digit
     * @return
     */
    fun formatNumber(`val`: Double, digit: Int): String {
        return formatNumber(BigDecimal(`val`), digit)
    }

    /**
     * 格式化实数
     *
     * @param val
     * @param digit
     * @return
     */
    fun formatNumber(`val`: BigDecimal, digit: Int): String {
        val buff = StringBuffer()

        buff.append("###0")

        if (digit > 0) {
            buff.append(".")

            for (i in 0 until digit) {
                buff.append("0")
            }
        }

        return formatNumber(`val`, buff.toString())
    }

    /**
     * 格式化实数
     *
     * @param val
     * @param pattern
     * @return
     */
    fun formatNumber(`val`: BigDecimal, pattern: String): String {
        val nf = NumberFormat.getNumberInstance() as DecimalFormat
        nf.applyPattern(pattern)
        return nf.format(`val`)
    }

    /**
     * 字节数组转换成十六进制字符串
     *
     * @param buf
     * @return
     */
    fun byteArrayToHexStr(buf: ByteArray): String {
        val sb = StringBuffer()
        var hex: String

        for (i in buf.indices) {
            hex = Integer.toHexString(buf[i].toInt())
            if (hex.length == 1) {
                sb.append("0")
            }
            sb.append(hex.toLowerCase())
        }

        return sb.toString()
    }

    /**
     * 十六进制字符串转换成字节数组
     *
     * @param hexStr
     * @return
     */
    fun hexStrToByteArray(hexStr: String): ByteArray? {
        if (isEmpty(hexStr)) {
            return null
        }

        val result = ByteArray(hexStr.length / 2)

        for (i in 0 until hexStr.length / 2) {
            val high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16)
            val low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16)

            result[i] = (high * 16 + low).toByte()
        }

        return result
    }

    /**
     * 将字符串中间变成星号，比如银行卡号 变成 6225********2345
     * @param frontLen 前端长度
     * @param rearLen 后端长度
     * @return
     */
    fun hideMidStr(src: String, frontLen: Int, rearLen: Int): String? {
        if (frontLen < 0 || rearLen < 0) {
            return src
        }

        if (isEmpty(src)) {
            return src
        }

        val len = src.length

        if (len <= frontLen + rearLen) {
            return src
        } else {
            val buff = StringBuffer(src.substring(0, frontLen))

            for (i in 0 until len - frontLen - rearLen) {
                buff.append("*")
            }

            buff.append(src.substring(len - rearLen))

            return buff.toString()
        }
    }

    /**
     * 判断密码强度
     * @param pswd
     * @param caseSensitive 是否区分大小写
     * @return 1：低，2：中，3：高，4：完美
     */
    fun getPasswordLevel(pswd: String, caseSensitive: Boolean): Int {
        var password = pswd
        var ch: Char
        var hasUpper = false // 包含大写字母
        var hasLower = false // 包含小写字母
        var hasSymbol = false // 包含符号
        var hasNumber = false // 包含数字

        if (isEmpty2(password)) {
            return 1
        }

        if (!caseSensitive) { // 如果大小写不敏感，全部转换成大写，最多只会返回3
            password = password.toUpperCase()
        }

        for (i in 0 until password.length) {
            ch = password[i]

            if (ch >= 'A' && ch <= 'Z') {
                hasUpper = true
            } else if (ch >= 'a' && ch <= 'z') {
                hasLower = true
            } else if (ch >= '0' && ch <= '9') {
                hasNumber = true
            } else { // 数字、字母以外的都算符号
                hasSymbol = true
            }
        }

        // 判断强度
        var count = 0

        if (hasUpper) {
            count++
        }
        if (hasLower) {
            count++
        }
        if (hasSymbol) {
            count++
        }
        if (hasNumber) {
            count++
        }

        return if (count > 1) count else 1
    }

    /**
     * 把参数连接成一个字符串，并在中间加上下划线(空指针会输出null)
     * 主要用于创建一个特有的字符串，比如缓存的key
     * @param args
     * @return
     */
    fun genQueryKey(vararg args: Any): String {
        val buff = StringBuffer()
        for (obj in args) {
            buff.append("_")

            if (obj == null) {
                buff.append("null")
            } else {
                buff.append(obj.toString())
            }
        }

        return buff.toString().replace(" ".toRegex(), "_")
    }
}

