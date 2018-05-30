package cptn.util

import org.slf4j.LoggerFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {
    /**
     * yyyy-MM-dd H:mm:ss
     */
    const val DTP_LONG = "yyyy-MM-dd H:mm:ss"

    /**
     * yyyy-M-d H:m:s
     */
    const val DTP_SHORT = "yyyy-M-d H:m:s"

    /**
     * yyyy-MM-dd
     */
    const val DP_LONG = "yyyy-MM-dd"

    /**
     * yyyy-M-d
     */
    const val DP_SHORT = "yyyy-M-d"

    /**
     * H:mm:ss
     */
    const val TP_LONG = "H:mm:ss"

    /**
     * H:m:s
     */
    const val TP_SHORT = "H:m:s"

    private val log = LoggerFactory.getLogger(DateUtil::class.java)

    val currentTime: Date
        get() = Date(System.currentTimeMillis())

    fun getCurDateStr(pattern: String): String {
        val df = SimpleDateFormat(pattern)
        return df.format(currentTime)
    }

    @JvmOverloads
    fun getDateStr(date: Date?, pattern: String = DP_LONG): String {
        if (date == null) {
            return ""
        }

        val df = SimpleDateFormat(pattern)
        return df.format(date)
    }

    /**
     * 判断时间是否过期
     * @param time
     * @return
     */
    fun isExpired(time: String, pattern: String): Boolean {
        var result = true

        try {
            val cur = DateUtil.currentTime
            val df = SimpleDateFormat(pattern)
            val dest = df.parse(time)

            if (dest.after(cur)) { // 过期时间大于当前时间，未过期
                result = false
            }

        } catch (ex: ParseException) {
            log.warn("", ex)
        }

        return result
    }

    /**
     * 判断时间是否已过期，单位：毫秒
     * @param expireTime
     * @return
     */
    fun isExpired(expireTime: Long): Boolean {
        return if (expireTime == 0L) {
            false
        } else expireTime <= System.currentTimeMillis()

    }

    /**
     * 解析日期
     * @param date
     * @param pattern
     * @return
     */
    fun parse(date: String, pattern: String): Date? {
        if (StrUtil.isEmpty2(date)) {
            return null
        }

        try {
            val df = SimpleDateFormat(pattern)
            return df.parse(date)
        } catch (e: Exception) {
            return null
        }

    }

    /**
     * 解析日期（yyyy-MM-dd 或  yyyy-M-d）
     * @param date
     * @return
     */
    fun parseDate(date: String): Date? {
        if (StrUtil.isEmpty2(date)) {
            return null
        }

        val pattern = if (date.length == 10) DP_LONG else DP_SHORT
        return parse(date, pattern)
    }
}
