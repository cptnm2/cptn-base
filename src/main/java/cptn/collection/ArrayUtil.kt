package cptn.collection

// 数组工具
object ArrayUtil {
    // 判断数组是否存在指定数字
    fun intInArr(arr: IntArray, key: Int): Boolean {
        for (i in arr) {
            if (i == key) {
                return true
            }
        }

        return false
    }


}