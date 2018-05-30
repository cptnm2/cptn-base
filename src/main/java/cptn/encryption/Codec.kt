package cptn.encryption

import org.apache.commons.codec.binary.Base64
import java.security.MessageDigest
import javax.crypto.KeyGenerator
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

// 加密工具类
abstract class Codec {
    companion object {
        const val KEY_SHA = "SHA"
        const val KEY_MD5 = "MD5"

        // 3DES cipher mode, compatible with .NET
        const val ALGORTHM_3DES = "DESede" // DESede / ECB / PKCS5Padding

        /**
         * MAC算法可选以下多种算法
         *
         * <pre>
         * HmacMD5
         * HmacSHA1
         * HmacSHA256
         * HmacSHA384
         * HmacSHA512
        </pre> *
         */
        const val KEY_MAC = "HmacMD5"

        /**
         * BASE64解密
         *
         * @param val
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun decryptBASE64(value: String): ByteArray {
            val base = Base64()
            return base.decode(value)
        }

        /**
         * BASE64加密
         *
         * @param val
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun encryptBASE64(value: ByteArray): String {
            val base = Base64()
            return base.encodeAsString(value)
        }

        /**
         * MD5 加密
         *
         * @param data
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun encryptMD5(data: ByteArray): ByteArray {
            val md5 = MessageDigest.getInstance(KEY_MD5)
            md5.update(data)

            return md5.digest()
        }

        /**
         * 初始化HMAC密钥
         *
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun initMacKey(): String {
            val keyGenerator = KeyGenerator.getInstance(KEY_MAC)

            val secretKey = keyGenerator.generateKey()
            return encryptBASE64(secretKey.encoded)
        }

        /**
         * HMAC 加密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun encryptHMAC(data: ByteArray, key: String): ByteArray {
            val secretKey = SecretKeySpec(decryptBASE64(key), KEY_MAC)
            val mac = Mac.getInstance(secretKey.algorithm)
            mac.init(secretKey)
            return mac.doFinal(data)
        }

        /**
         * MD5 加密
         * @param data
         * @return
         */
        fun getMd5(data: String): ByteArray? {
            val unencodedPassword = data.toByteArray()
            var md: MessageDigest? = null
            try {
                md = MessageDigest.getInstance(KEY_MD5)
            } catch (e: Exception) {
                return null
            }

            md!!.reset()
            md.update(unencodedPassword)
            return md.digest()
        }

        /**
         * SHA 加密
         *
         * @param data
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getSHA(data: ByteArray): ByteArray {
            val sha = MessageDigest.getInstance(KEY_SHA)
            sha.update(data)

            return sha.digest()
        }

        /**
         * SHA1 加密
         * @param src
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getSha1(src: String): ByteArray {
            val crypt = MessageDigest.getInstance("SHA-1")
            crypt.reset()
            crypt.update(src.toByteArray(charset("UTF-8")))
            return crypt.digest()
        }
    }
}