package cptn.util

import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

// 对象序列化工具
object SerialUtil {
    private val logger = LoggerFactory.getLogger(SerialUtil::class.java)

    // 序列化对象
    fun serialize(obj: Any): ByteArray? {
        try {
            val baos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(baos)

            oos.writeObject(obj)

            return baos.toByteArray()
        } catch (e: Exception) {
            logger.error("序列化对象失败！", e)
        }

        return null
    }

    // 反序列化对象
    fun deserialize(bytes: ByteArray): Any? {
        try {
            val bais = ByteArrayInputStream(bytes)
            val ois = ObjectInputStream(bais)

            return ois.readObject()
        } catch (e: Exception) {
            logger.warn("反序列化对象失败!", e)
        }

        return null
    }
}
