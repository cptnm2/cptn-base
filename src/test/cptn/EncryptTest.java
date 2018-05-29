package cptn;

import cptn.encryption.AesDecoder;
import cptn.encryption.AesEncoder;
import cptn.encryption.Coder;
import cptn.util.StrUtil;
import org.junit.Assert;
import org.junit.Test;

public class EncryptTest {

	/**
	 * 请注意: 默认JDK只包含128位AES，要想使用256位AES，必须要替换JDK中的包（请谷歌）
	 * @throws Exception
	 */
	@Test
	public void testAes() throws Exception {
		int keyLen = 128;
		String key = "Dlg5794sp3cuidvpgakk773kdh201uty";
		String origin = "Hello, and welcome to the 11th top gear special. 童鞋，欢迎使用AES加密！";

		AesEncoder enc = new AesEncoder(key, keyLen);
		AesDecoder dec = new AesDecoder(key, keyLen);

		String dest = Coder.encryptBASE64(enc.encode(origin.getBytes()));
		System.out.println("Encrypted:\n" + dest);
		
		String src = new String(dec.decode(Coder.decryptBASE64(dest)), "UTF-8").trim();
		System.out.println("Decrypted:\n" + src);
		
		Assert.assertEquals(origin, src);
	}
	
	@Test
	public void testMd5() {
		String src = "f9867646922249eeb6c42fa52475a68a9ec509094f624f039a5ff7c9a5c7a7f86ICB546LLemjjuaatOeDiOmFkg==114653711516130c0c976cbef34510aa5b62b38f579793";
		
		String dest = StrUtil.INSTANCE.byteArrayToHexStr(Coder.getMd5(src));
		
		System.out.println(dest);
	}
}
