package cptn.util

import java.math.BigDecimal
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

// 字符串处理工具
object StrUtil {
    // 系统换行符
    val fileSep: String
        get() = System.getProperty("file.separator")

    // 判断字符串是否为空（null || ""）
    fun isEmpty(value: String?): Boolean {
        return null == value || "" == value
    }

    // 判断字符串是否为空（null || "" || "   "）
    fun isEmpty2(value: String?): Boolean {
        var s = value?.trim()
        return null == s || "" == s
    }

    // 安全去除两端空格（当值为 null 时返回 null）
    fun safeTrim(value: String?): String? {
        return value?.trim { it <= ' ' }
    }

    // 安全去除两端空格（当值为 null 时返回 ""）
    fun safeTrim2(value: String?): String {
        return if (value == null) {
            ""
        } else value.trim { it <= ' ' }
    }

    // 比较字符串是否相等
    fun isStrEqual(a: String?, b: String?, ignoreCase: Boolean): Boolean {
        return if (a == null && b == null) {
            true
        } else if (a == null || b == null) {
            false
        } else {
            a.equals(b, ignoreCase)
        }
    }

    // 判断两个字符串是否相等(区分大小写)
    fun isEqual(a: String?, b: String?): Boolean {
        return isStrEqual(a, b, true)
    }

    /**
     * 判断两个字符串是否相等(不分大小写)
     *
     * @param a
     * @param b
     * @return
     */
    fun isEqualIgnoreCase(a: String?, b: String?): Boolean {
        return isStrEqual(a, b, false)
    }

    // 安全将字符串转换成数字
    @JvmOverloads
    fun safeParseInt(value: String?, defValue: Int = 0): Int {
        try {
            return value?.toInt() ?: defValue
        } catch (e: Exception) {
            return defValue
        }
    }

    // 安全将字符串转换成资金
    @JvmOverloads
    fun safeParseDecimal(value: String, defValue: BigDecimal = BigDecimal.ZERO): BigDecimal {
        try {
            return BigDecimal(value)
        } catch (e: Exception) {
            return defValue
        }
    }

    // 生成36位的 GUID，其中包含4个"-"
    fun genUUID(): String {
        return UUID.randomUUID().toString()
    }

    // 生成32位的 GUID，不包含"-"
    fun genUUID32(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    // 首字符大写
    fun firstCharUpper(value: String?): String {
        return if (value == null) {
            ""
        } else value.substring(0, 1).toUpperCase() + value.substring(1)
    }

    // 判断是否中文字符
    fun isChinese(c: Char): Boolean {
        val ub = Character.UnicodeBlock.of(c)

        return (ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
    }

    // 判断是否包含中文字符
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

    fun truncHead(src: String, maxLength: Int): String {
        if (isEmpty(src) || maxLength <= 0) {
            return ""
        }

        var totalLen = 0
        for (i in 0 until src.length) {
            totalLen += if (isChinese(src[i])) 2 else 1

            if (totalLen > maxLength) {
                return src.substring(0, i)
            } else if (totalLen == maxLength) {
                return src.substring(0, i + 1)
            }
        }

        return src
    }

    // 截取指定长度字符串，中文字符长度为2，如果字符串被截断, 自动加上后缀
    fun truncHead2(src: String, maxLength: Int, suffix: String): String {
        var dst = truncHead(src, maxLength)

        if (dst.length < src.length) {
            dst += suffix
        }

        return dst
    }

    fun ansiToUtf8(param: String): String {
        return if (param == null) {
            ""
        }
        else {
            String(param.toByteArray(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"))
        }
    }

    fun utf8ToAnsi(param: String?): String {
        return if (param == null) {
            ""
        }
        else {
            String(param.toByteArray(Charset.forName("UTF-8")), Charset.forName("ISO-8859-1"))
        }
    }

    fun ansiToGBK(param: String): String {
        return if (param == null) {
            ""
        }
        else {
            String(param.toByteArray(Charset.forName("ISO-8859-1")), Charset.forName("GBK"))
        }
    }

    // 分解字符串，并将每个元素安全去掉空格
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

    // 生成随机字符串
    fun genRandomStr(chars: String, len: Int): String {
        if (chars.isEmpty() || len <= 0) {
            return ""
        }

        val buff = StringBuffer()
        val ran = Random()

        for (i in 0..len) {
            val pos = ran.nextInt(chars.length)
            buff.append(chars[pos])
        }

        return buff.toString()
    }

    // 连接多个字符串
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

    // 格式化金额
    fun formatMoney(value: BigDecimal): String {
        return formatNumber(value, "###0.00")
    }

    // 格式化实数
    fun formatNumber(value: Float, digit: Int): String {
        return formatNumber(BigDecimal(value.toDouble()), digit)
    }

    // 格式化实数
    fun formatNumber(value: Double, digit: Int): String {
        return formatNumber(BigDecimal(value), digit)
    }

    // 格式化实数
    fun formatNumber(value: BigDecimal, digit: Int): String {
        val buff = StringBuffer()

        buff.append("###0")

        if (digit > 0) {
            buff.append(".")

            for (i in 0 until digit) {
                buff.append("0")
            }
        }

        return formatNumber(value, buff.toString())
    }

    // 格式化实数
    fun formatNumber(value: BigDecimal, pattern: String): String {
        val nf = NumberFormat.getNumberInstance() as DecimalFormat
        nf.applyPattern(pattern)
        return nf.format(value)
    }

    // 字节数组转换成十六进制字符串
    fun byteArrayToHexStr(arr: ByteArray): String {
        val sb = StringBuffer()
        var hex: String

        for (b in arr) {
            hex = Integer.toHexString(b.toInt())

            if (hex.length == 1) {
                sb.append("0")
            }

            sb.append(hex)
        }

        return sb.toString()
    }

    // 十六进制字符串转换成字节数组
    fun hexStrToByteArray(hexStr: String): ByteArray {
        if (hexStr.isEmpty()) {
            return ByteArray(0)
        }

        val len: Int = hexStr.length / 2
        val arr = ByteArray(len)

        for (i in 0..len) {
            val high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16)
            val low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16)

            arr[i] = (high * 16 + low).toByte()
        }

        return arr
    }

    // 将字符串中间变成星号，比如银行卡号 变成 6225********2345
    fun hideMidStr(src: String, frontLen: Int, rearLen: Int): String? {
        if (frontLen < 0 || rearLen < 0) {
            return src
        }

        if (src.isEmpty()) {
            return ""
        }

        val len = src.length

        if (len <= frontLen + rearLen) {
            return src
        } else {
            val buff = StringBuffer(src.substring(0, frontLen))
            val starLen = len - frontLen - rearLen

            for (i in 0..starLen) {
                buff.append("*")
            }

            buff.append(src.substring(len - rearLen))
            return buff.toString()
        }
    }

    // 判断密码强度（1：低，2：中，3：高，4：完美）
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

            if (ch in 'A'..'Z') {
                hasUpper = true
            } else if (ch in 'a'..'z') {
                hasLower = true
            } else if (ch in '0'..'9') {
                hasNumber = true
            } else {
                // 数字、字母以外的都算符号
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

        return count
    }

    // 把参数连接成一个字符串，并在中间加上下划线（空指针会输出 null）
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

