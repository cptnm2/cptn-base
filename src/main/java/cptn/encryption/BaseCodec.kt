package cptn.encryption

interface Encrypt {
    @Throws(Exception::class)
    fun encode(src: ByteArray): ByteArray
}


interface Decrypt {
    @Throws(Exception::class)
    fun decode(src: ByteArray): ByteArray
}


open class BaseCodec {
    open var algo: String = ""
    open var key: String = ""
    open var keyLength: Int = 0

    // 构建秘钥
    fun buildKeyData(data: ByteArray, len: Int): ByteArray {
        val key = ByteArray(len) // 声明一个24位的字节数组，默认里面都是0

        if (key.size > data.size) {
            System.arraycopy(data, 0, key, 0, data.size)
        } else {
            System.arraycopy(data, 0, key, 0, key.size)
        }

        return key
    }
}
