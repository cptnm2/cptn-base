package cptn.collection

import java.util.*

// 集合类工具
object CollectionUtil {
    /**
     * 对列表进行排序
     * @param list
     */
    fun sortList(list: List<String>) {
        Collections.sort(list) { a, b -> a.compareTo(b) }
    }
}
