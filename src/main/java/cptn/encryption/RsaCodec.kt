package cptn.encryption

import java.security.Key
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.Signature
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

// 非对称加密工具类
abstract class RsaCodec : Codec() {
    companion object {
        const val KEY_ALGORITHM = "RSA"
        const val SIGNATURE_ALGORITHM = "MD5withRSA"

        const val PUBLIC_KEY = "RSAPublicKey"
        const val PRIVATE_KEY = "RSAPrivatekey"

        /**
         * 用私钥对信息生成数字签名
         *
         * @param data 加密数据
         * @param privateKey 私钥
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun sign(data: ByteArray, privateKey: String): String {
            // 解密由base64编码的私钥
            val keyBytes = Codec.Companion.decryptBASE64(privateKey)

            val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(keyBytes)

            val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)

            // 取私钥对象
            val pKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec)

            // 用私钥生成数字签名
            val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
            signature.initSign(pKey)
            signature.update(data)

            return Codec.Companion.encryptBASE64(signature.sign())
        }

        /**
         * 校验数字签名
         *
         * @param data 加密数据
         * @param publicKey 公钥
         * @param sign 数字签名
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun verify(data: ByteArray, publicKey: String, sign: String): Boolean {

            // 解密有base64编码的公钥
            val keyBytes = Codec.Companion.decryptBASE64(publicKey)

            val keySpec = X509EncodedKeySpec(keyBytes)

            val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)

            // 取公钥对象
            val pKey = keyFactory.generatePublic(keySpec)

            val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
            signature.initVerify(pKey)
            signature.update(data)
            // 验证签名是否正常
            return signature.verify(Codec.Companion.decryptBASE64(sign))
        }

        /**
         * 解密 用私钥解密
         *
         * @param data 加密数据
         * @param key
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun decryptPrivateKey(data: ByteArray, key: String): ByteArray {
            val keyBytes = Codec.Companion.decryptBASE64(key)

            // 取得私钥
            val encodedKeySpec = PKCS8EncodedKeySpec(keyBytes)
            val factory = KeyFactory.getInstance(KEY_ALGORITHM)
            val pKey = factory.generatePrivate(encodedKeySpec)

            // 对数据解密
            val cipher = Cipher.getInstance(factory.algorithm)
            cipher.init(Cipher.DECRYPT_MODE, pKey)

            return cipher.doFinal(data)
        }

        /**
         * 用公钥解密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun decryptByPublicKey(data: ByteArray, key: String): ByteArray {

            // 解密
            val keyBytes = Codec.Companion.decryptBASE64(key)

            // 取得公钥
            val keySpec = X509EncodedKeySpec(keyBytes)
            val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
            val pKey = keyFactory.generatePublic(keySpec)

            // 对数据解密
            val cipher = Cipher.getInstance(keyFactory.algorithm)
            cipher.init(Cipher.DECRYPT_MODE, pKey)

            return cipher.doFinal(data)
        }

        /**
         * 用公钥加密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun encryptByPublicKey(data: ByteArray, key: String): ByteArray {

            val keyBytes = Codec.Companion.decryptBASE64(key)

            val keySpec = X509EncodedKeySpec(keyBytes)
            val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
            val pKey = keyFactory.generatePublic(keySpec)

            val cipher = Cipher.getInstance(keyFactory.algorithm)
            cipher.init(Cipher.ENCRYPT_MODE, pKey)

            return cipher.doFinal(data)
        }

        /**
         * 用私钥加密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun encryptByPrivateKey(data: ByteArray, key: String): ByteArray {

            val keyBytes = Codec.Companion.decryptBASE64(key)

            val keySpec = PKCS8EncodedKeySpec(keyBytes)
            val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
            val privateKey = keyFactory.generatePrivate(keySpec)

            val cipher = Cipher.getInstance(keyFactory.algorithm)
            cipher.init(Cipher.ENCRYPT_MODE, privateKey)

            return cipher.doFinal(data)
        }

        /**
         * 取得私钥
         *
         * @param keyMap
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getPrivateKey(keyMap: Map<String, Any>): String {

            val key = keyMap[PRIVATE_KEY] as Key

            return Codec.Companion.encryptBASE64(key.encoded)
        }

        /**
         * 取得公钥
         *
         * @param keyMap
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getPublicKey(keyMap: Map<String, Any>): String {

            val key = keyMap[PUBLIC_KEY] as Key

            return Codec.Companion.encryptBASE64(key.encoded)
        }

        /**
         * 初始化密钥
         *
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun initKey(): Map<String, Any> {

            val keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM)
            keyPairGenerator.initialize(1024)

            val keyPair = keyPairGenerator.generateKeyPair()

            // 公钥
            val publicKey = keyPair.public as RSAPublicKey

            // 私钥
            val privateKey = keyPair.private as RSAPrivateKey

            val keyMap = HashMap<String, Any>(2)
            keyMap[PRIVATE_KEY] = privateKey
            keyMap[PUBLIC_KEY] = publicKey
            return keyMap
        }
    }
}