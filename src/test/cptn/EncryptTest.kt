package cptn

import cptn.encryption.AesDecoder
import cptn.encryption.AesEncoder
import cptn.encryption.Codec
import cptn.util.StrUtil
import org.junit.Assert.*
import org.junit.Test
import java.nio.charset.Charset

class EncryptTest {

    /**
     * 请注意: 默认JDK只包含128位AES，要想使用256位AES，必须要替换JDK中的包（请谷歌）
     * @throws Exception
     */
    @Test
    @Throws(Exception::class)
    fun testAes() {
        val keyLen = 128
        val key = "Dlg5794sp3cuidvpgakk773kdh201uty"
        val origin = "Hello, and welcome to the 11th top gear special. 童鞋，欢迎使用AES加密！"

        val enc = AesEncoder(key, keyLen)
        val dec = AesDecoder(key, keyLen)

        val dest = Codec.encryptBASE64(enc.encode(origin.toByteArray()))

        val src = String(dec.decode(Codec.decryptBASE64(dest)), Charset.forName("UTF-8")).trim { it <= ' ' }

        println("Original:\n$origin")
        println("Encrypted:\n$dest")
        println("Decrypted:\n$src")

        assertEquals(origin, src)
    }

    @Test
    fun testMd5() {
        val src = "f9867646922249eeb6c42fa52475a68a9ec509094f624f039a5ff7c9a5c7a7f86ICB546LLemjjuaatOeDiOmFkg==114653711516130c0c976cbef34510aa5b62b38f579793"

        val dest = StrUtil.byteArrayToHexStr(Codec.getMd5(src)!!)

        println(dest)
    }
}
