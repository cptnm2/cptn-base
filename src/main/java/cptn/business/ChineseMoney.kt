package cptn.business

import java.math.BigDecimal

import cptn.util.MathUtil

// 中文金钱的运算工具
object ChineseMoney {
    private val chineseDigits = arrayOf("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖")

    fun toChinese(amount: Int): String {
        return toChinese(BigDecimal(amount))
    }

    fun toChinese(amount: Float): String {
        return toChinese(BigDecimal(amount.toDouble()))
    }

    fun toChinese(amount: Double): String {
        return toChinese(BigDecimal(amount))
    }

    fun toChinese(money: BigDecimal?): String {
        if (money == null) {
            return ""
        }

        val negative = money.compareTo(BigDecimal.ZERO) < 0
        val amount = money.abs().multiply(BigDecimal(100))

        val `val` = MathUtil.format(amount, 0)

        var x = java.lang.Long.parseLong(`val`)

        val numFen = (x % 10).toInt()
        x /= 10
        val numJiao = (x % 10).toInt()
        x /= 10

        val parts = IntArray(20)
        var numParts = 0

        var temp = x
        var i = 0
        while (temp != 0L) {
            parts[i] = (temp % 10000).toInt()
            ++numParts
            temp /= 10000
            ++i
        }

        var beforeWanIsZero = true
        var chineseStr = ""
        for (j in 0 until numParts) {
            val partChinese = partTranslate(parts[j])
            if (j % 2 == 0) {
                beforeWanIsZero = "" == partChinese
            }
            if (j != 0) {
                if (j % 2 == 0) {
                    chineseStr = "亿$chineseStr"
                } else if ("" == partChinese && !beforeWanIsZero) {
                    chineseStr = "零$chineseStr"
                } else {
                    if (parts[j - 1] < 1000 && parts[j - 1] > 0) {
                        chineseStr = "零$chineseStr"
                    }
                    chineseStr = "万$chineseStr"
                }
            }
            chineseStr = partChinese + chineseStr
        }

        if ("" == chineseStr) {
            chineseStr = chineseDigits[0]
        } else if (negative) {
            chineseStr = "负$chineseStr"
        }

        chineseStr += "元"

        if (numFen == 0 && numJiao == 0) {
            chineseStr += "整"
        } else if (numFen == 0) {
            chineseStr += chineseDigits[numJiao] + "角"
        } else if (numJiao == 0) {
            chineseStr += "零" + chineseDigits[numFen] + "分"
        } else {
            chineseStr += (chineseDigits[numJiao] + "角"
                    + chineseDigits[numFen] + "分")
        }
        return chineseStr
    }

    private fun partTranslate(amountPart: Int): String {
        if (amountPart < 0 || amountPart > 10000) {
            throw IllegalArgumentException(
                    "参数必须是大于等于 0，小于 10000 的整数！")
        }
        val units = arrayOf("", "拾", "佰", "仟")
        var temp = amountPart
        val amountStr = amountPart.toString()
        val amountStrLength = amountStr.length
        var lastIsZero = true
        var chineseStr = ""
        var i = 0
        while (i < amountStrLength && temp != 0) {
            val digit = temp % 10
            if (digit == 0) {
                if (!lastIsZero) {
                    chineseStr = "零$chineseStr"
                }
                lastIsZero = true
            } else {
                chineseStr = (chineseDigits[digit]
                        + units[i] + chineseStr)
                lastIsZero = false
            }
            temp /= 10
            ++i
        }
        return chineseStr
    }
}
