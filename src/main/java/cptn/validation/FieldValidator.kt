package cptn.validation

import cptn.util.StrUtil
import org.slf4j.LoggerFactory
import java.util.regex.Pattern

/**
 * 字段校验工具
 *
 * @author cptn
 */
object FieldValidator {
    private val logger = LoggerFactory.getLogger(FieldValidator::class.java)

    fun isNull(value: Any?): Boolean {
        return value == null
    }

    /**
     * 匹配URL地址
     *
     * @param str
     * @return
     */
    fun isUrl(str: String): Boolean {
        return match(str, "^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")
    }

    /**
     * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
     *
     * @param str
     * @return
     */
    fun isPwd(str: String): Boolean {
        return match(str, "^[a-zA-Z]\\w{6,12}$")
    }

    /**
     * 验证字符，只能包含中文、英文、数字、下划线等字符。
     *
     * @param str
     * @return
     */
    fun stringCheck(str: String): Boolean {
        return match(str, "^[a-zA-Z0-9\u4e00-\u9fa5-_]+$")
    }

    /**
     * 匹配Email地址
     *
     * @param str
     * @return
     */
    fun isEmail(str: String): Boolean {
        return match(str, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")
    }

    /**
     * 匹配非负整数（正整数+0）
     *
     * @param str
     * @return
     */
    fun isInteger(str: String): Boolean {
        return match(str, "^[+]?\\d+$")
    }

    /**
     * 判断数值类型，包括整数和浮点数
     *
     * @param str
     * @return
     */
    fun isNumeric(str: String): Boolean {
        return if (isFloat(str) || isInteger(str)) true else false
    }

    /**
     * 只能输入数字
     *
     * @param str
     * @return
     */
    fun isDigits(str: String): Boolean {
        return match(str, "^[0-9]*$")
    }

    /**
     * 匹配正浮点数
     *
     * @param str
     * @return
     */
    fun isFloat(str: String): Boolean {
        return match(str, "^[-\\+]?\\d+(\\.\\d+)?$")
    }

    /**
     * 联系电话(手机/电话皆可)验证
     *
     * @param text
     * @return
     */
    fun isTel(text: String): Boolean {
        return if (isMobile(text) || isPhone(text)) true else false
    }

    /**
     * 电话号码验证
     *
     * @param text
     * @return
     */
    fun isPhone(text: String): Boolean {
        return match(text, "^(\\d{3,4}-?)?\\d{7,9}$")
    }

    /**
     * 手机号码验证
     *
     * @param text
     * @return
     */
    fun isMobile(text: String?): Boolean {
        return if (text == null || text.length != 11) {
            false
        } else match(text, "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$")
    }

    /**
     * 身份证号码验证
     *
     * @param text
     * @return
     */
    fun isIdCardNo(text: String): Boolean {
        return match(text, "^(\\d{6})()?(\\d{4})(\\d{2})(\\d{2})(\\d{3})(\\w)$")
    }

    /**
     * 邮政编码验证
     *
     * @param text
     * @return
     */
    fun isZipCode(text: String): Boolean {
        return match(text, "^[0-9]{6}$")
    }

    /**
     * 判断整数num是否等于0
     *
     * @param num
     * @return
     */
    fun isIntEqZero(num: Int): Boolean {
        return num == 0
    }

    /**
     * 判断整数num是否大于0
     *
     * @param num
     * @return
     */
    fun isIntGtZero(num: Int): Boolean {
        return num > 0
    }

    /**
     * 判断整数num是否大于或等于0
     *
     * @param num
     * @return
     */
    fun isIntGteZero(num: Int): Boolean {
        return num >= 0
    }

    /**
     * 判断浮点数num是否等于0
     *
     * @param num 浮点数
     * @return
     */
    fun isFloatEqZero(num: Float): Boolean {
        return num == 0f
    }

    /**
     * 判断浮点数num是否大于0
     *
     * @param num 浮点数
     * @return
     */
    fun isFloatGtZero(num: Float): Boolean {
        return num > 0f
    }

    /**
     * 判断浮点数num是否大于或等于0
     *
     * @param num 浮点数
     * @return
     */
    fun isFloatGteZero(num: Float): Boolean {
        return num >= 0f
    }

    /**
     * 判断是否为合法字符(a-zA-Z0-9-_)
     *
     * @param text
     * @return
     */
    fun isRightfulString(text: String): Boolean {
        return match(text, "^[A-Za-z0-9_-]+$")
    }

    /**
     * 判断英文字符(a-zA-Z)
     *
     * @param text
     * @return
     */
    fun isEnglish(text: String): Boolean {
        return match(text, "^[A-Za-z]+$")
    }

    /**
     * 判断中文字符(包括汉字和符号)
     *
     * @param text
     * @return
     */
    fun isChineseChar(text: String): Boolean {
        return match(text, "^[\u0391-\uFFE5]+$")
    }

    /**
     * 匹配汉字
     *
     * @param text
     * @return
     */
    fun isChinese(text: String): Boolean {
        return match(text, "^[\u4e00-\u9fa5]+$")
    }

    /**
     * 是否包含中英文特殊字符，除英文"-_"字符外
     *
     * @param text
     * @return
     */
    fun isContainsSpecialChar(text: String): Boolean {
        if (StrUtil.isEmpty(text))
            return false
        val chars = arrayOf("[", "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", "|", "{", "}", "'", ":", ";", "'", ",", "[", "]", ".", "<", ">", "/", "?", "~", "！", "@", "#", "￥", "%", "…", "&", "*", "（", "）", "—", "+", "|", "{", "}", "【", "】", "‘", "；", "：", "”", "“", "’", "。", "，", "、", "？", "]")
        for (ch in chars) {
            if (text.contains(ch))
                return true
        }
        return false
    }

    /**
     * 过滤中英文特殊字符，除英文"-_"字符外
     *
     * @param text
     * @return
     */
    fun stringFilter(text: String): String {
        val regExpr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"
        val p = Pattern.compile(regExpr)
        val m = p.matcher(text)
        return m.replaceAll("").trim { it <= ' ' }
    }

    /**
     * 过滤html代码
     *
     * @param inputString  含html标签的字符串
     * @param removeSpaces 是否去掉空格
     * @return
     */
    @JvmOverloads
    fun htmlFilter(inputString: String, removeSpaces: Boolean = true): String {
        var htmlStr = inputString // 含html标签的字符串
        var textStr = ""
        val p_script: java.util.regex.Pattern
        val m_script: java.util.regex.Matcher
        val p_style: java.util.regex.Pattern
        val m_style: java.util.regex.Matcher
        val p_html: java.util.regex.Pattern
        val m_html: java.util.regex.Matcher
        val p_ba: java.util.regex.Pattern
        val m_ba: java.util.regex.Matcher

        try {
            val regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>" // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            val regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>" // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            val regEx_html = "<[^>]+>" // 定义HTML标签的正则表达式
            val patternStr = "\\s+"

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE)
            m_script = p_script.matcher(htmlStr)
            htmlStr = m_script.replaceAll("") // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE)
            m_style = p_style.matcher(htmlStr)
            htmlStr = m_style.replaceAll("") // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE)
            m_html = p_html.matcher(htmlStr)
            htmlStr = m_html.replaceAll("") // 过滤html标签

            if (removeSpaces) {
                p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE)
                m_ba = p_ba.matcher(htmlStr)
                htmlStr = m_ba.replaceAll("") // 过滤空格
            }

            textStr = htmlStr

        } catch (e: Exception) {
            logger.error("", e)
        }

        return textStr// 返回文本字符串
    }

    /**
     * 正则表达式匹配
     *
     * @param text 待匹配的文本
     * @param reg  正则表达式
     * @return
     */
    private fun match(text: String, reg: String): Boolean {
        return if (StrUtil.isEmpty(text) || StrUtil.isEmpty(reg)) {
            false
        } else Pattern.compile(reg).matcher(text).matches()
    }

    // 附 ： 常用的正则表达式：
    // 匹配特定数字：
    // ^[1-9]d*$ //匹配正整数
    // ^-[1-9]d*$ //匹配负整数
    // ^-?[1-9]d*$ //匹配整数
    // ^[1-9]d*|0$ //匹配非负整数（正整数 + 0）
    // ^-[1-9]d*|0$ //匹配非正整数（负整数 + 0）
    // ^[1-9]d*.d*|0.d*[1-9]d*$ //匹配正浮点数
    // ^-([1-9]d*.d*|0.d*[1-9]d*)$ //匹配负浮点数
    // ^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$ //匹配浮点数
    // ^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$ //匹配非负浮点数（正浮点数 + 0）
    // ^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$ //匹配非正浮点数（负浮点数 + 0）
    // 评注：处理大量数据时有用，具体应用时注意修正
    //
    // 匹配特定字符串：
    // ^[A-Za-z]+$ //匹配由26个英文字母组成的字符串
    // ^[A-Z]+$ //匹配由26个英文字母的大写组成的字符串
    // ^[a-z]+$ //匹配由26个英文字母的小写组成的字符串
    // ^[A-Za-z0-9]+$ //匹配由数字和26个英文字母组成的字符串
    // ^w+$ //匹配由数字、26个英文字母或者下划线组成的字符串
    //
    // 在使用RegularExpressionValidator验证控件时的验证功能及其验证表达式介绍如下:
    //
    // 只能输入数字：“^[0-9]*$”
    // 只能输入n位的数字：“^d{n}$”
    // 只能输入至少n位数字：“^d{n,}$”
    // 只能输入m-n位的数字：“^d{m,n}$”
    // 只能输入零和非零开头的数字：“^(0|[1-9][0-9]*)$”
    // 只能输入有两位小数的正实数：“^[0-9]+(.[0-9]{2})?$”
    // 只能输入有1-3位小数的正实数：“^[0-9]+(.[0-9]{1,3})?$”
    // 只能输入非零的正整数：“^+?[1-9][0-9]*$”
    // 只能输入非零的负整数：“^-[1-9][0-9]*$”
    // 只能输入长度为3的字符：“^.{3}$”
    // 只能输入由26个英文字母组成的字符串：“^[A-Za-z]+$”
    // 只能输入由26个大写英文字母组成的字符串：“^[A-Z]+$”
    // 只能输入由26个小写英文字母组成的字符串：“^[a-z]+$”
    // 只能输入由数字和26个英文字母组成的字符串：“^[A-Za-z0-9]+$”
    // 只能输入由数字、26个英文字母或者下划线组成的字符串：“^w+$”
    // 验证用户密码:“^[a-zA-Z]\\w{5,17}$”正确格式为：以字母开头，长度在6-18之间，
    //
    // 只能包含字符、数字和下划线。
    // 验证是否含有^%&’,;=?$”等字符：“[^%&’,;=?$x22]+”
    // 只能输入汉字：“^[u4e00-u9fa5],{0,}$”
    // 验证Email地址：“^w+[-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$”
    // 验证InternetURL：“^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$”
    // 验证电话号码：“^((d{3,4})|d{3,4}-)?d{7,8}$”
    //
    // 正确格式为：“XXXX-XXXXXXX”，“XXXX-XXXXXXXX”，“XXX-XXXXXXX”，
    //
    // “XXX-XXXXXXXX”，“XXXXXXX”，“XXXXXXXX”。
    // 验证身份证号（15位或18位数字）：“^d{15}|d{}18$”
    // 验证一年的12个月：“^(0?[1-9]|1[0-2])$”正确格式为：“01”-“09”和“1”“12”
    // 验证一个月的31天：“^((0?[1-9])|((1|2)[0-9])|30|31)$” 正确格式为：“01”“09”和“1”“31”。
    //
    // 匹配中文字符的正则表达式： [u4e00-u9fa5]
    // 匹配双字节字符(包括汉字在内)：[^x00-xff]
    // 匹配空行的正则表达式：n[s| ]*r
    // 匹配HTML标记的正则表达式：/< (.*)>.*|< (.*) />/
    // 匹配首尾空格的正则表达式：(^s*)|(s*$)
    // 匹配Email地址的正则表达式：w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*
    // 匹配网址URL的正则表达式：^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$
}
