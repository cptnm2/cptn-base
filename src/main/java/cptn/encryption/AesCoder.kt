package cptn.encryption

import java.security.InvalidParameterException
import java.security.Key
import java.security.SecureRandom
import javax.crypto.Cipher

import javax.crypto.KeyGenerator

/**
 * AES 加密/解密工具
 * @author cptn
 */
open class Aes(key: String, keyLen: Int) : BaseCoder() {
    companion object {
        // 算法
        val KEY_ALGORITHM = "AES"

        // 模式
        val DEF_ENC_ALGORITHM = "AES/ECB/PKCS5Padding"
        val DEF_DEC_ALGORITHM = "AES/ECB/NoPadding"

        // 加密位数
        val KEY_LEN_128 = 128
        val KEY_LEN_256 = 256
    }

    override var keyLength: Int = 128
        set(value) {
            if (value == Aes.KEY_LEN_128 || value == Aes.KEY_LEN_256) {
                field = value
            } else {
                throw InvalidParameterException()
            }
        }

    init {
        this.key = key
        keyLength = keyLen
    }

    /**
     * 构建密钥
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    protected fun buildKey(): Key {
        val kgen = KeyGenerator.getInstance(Aes.KEY_ALGORITHM)

        val random = SecureRandom.getInstance("SHA1PRNG")
        random.setSeed(key.toByteArray())
        kgen.init(keyLength, random)

        return kgen.generateKey()
    }
}


class AesEncoder(key: String, keyLen: Int) : Aes(key, keyLen), Encrypt {
    init {
        algo = Aes.DEF_ENC_ALGORITHM
    }

    @Throws(Exception::class)
    override fun encode(data: ByteArray): ByteArray {
        val sk = buildKey()

        //实例化
        val cipher = Cipher.getInstance(algo)

        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, sk)

        //执行操作
        return cipher.doFinal(data)
    }
}

class AesDecoder(key: String, keyLen: Int) : Aes(key, keyLen), Decrypt {
    init {
        algo = Aes.DEF_DEC_ALGORITHM
    }

    @Throws(Exception::class)
    override fun decode(data: ByteArray): ByteArray {
        val sk = buildKey()

        //实例化
        val cipher = Cipher.getInstance(algo)

        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, sk)

        //执行操作
        return cipher.doFinal(data)
    }
}
