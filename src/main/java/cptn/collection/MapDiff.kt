package cptn.collection

import java.util.ArrayList

abstract class MapDiff<K> {
    private val r1: MutableList<K> // map1 中特有的值
    private val r2: MutableList<K> // map2 中特有的值

    val result1: List<K>
        get() = r1

    val result2: List<K>
        get() = r2

    init {
        r1 = ArrayList()
        r2 = ArrayList()
    }

    fun diff(map1: Map<K, *>, map2: Map<K, *>) {
        findUniqueSrc(map1, map2, r1)
        findUniqueSrc(map2, map1, r2)
    }

    protected fun findUniqueSrc(src: Map<K, *>, dest: Map<K, *>, result: MutableList<K>) {
        result.clear()

        val iter = src.keys.iterator()
        while (iter.hasNext()) {
            val k = iter.next()

            if (dest.containsKey(k)) { // 两个Map共有的
                continue
            } else {
                result.add(k) // 特有值存放在结果集
            }
        }
    }

    protected abstract fun isEqual(k1: K, k2: K): Boolean
}


class IntMapDiff : MapDiff<Int>() {
    override fun isEqual(k1: Int, k2: Int): Boolean {
        return k1 == k2
    }
}


class StrMapDiff : MapDiff<String>() {
    override fun isEqual(k1: String, k2: String): Boolean {
        return k1.equals(k2, true)
    }
}