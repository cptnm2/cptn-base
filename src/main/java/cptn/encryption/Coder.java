package cptn.encryption;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 * @author cptn
 *
 */
public abstract class Coder {
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	// 3DES cipher mode, compatible with .NET
	public static final String ALGORTHM_3DES = "DESede"; // DESede / ECB / PKCS5Padding

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param val
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String val) throws Exception {
		Base64 base = new Base64();
		return base.decode(val);
	}

	/**
	 * BASE64加密
	 * 
	 * @param val
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] val) throws Exception {
		Base64 base = new Base64();
		return base.encodeAsString(val);
	}

	/**
	 * MD5 加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();
	}
	
	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC 加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	/**
	 * MD5 加密
	 * @param data
	 * @return
	 */
    public static byte[] getMd5(final String data) {
        byte[] unencodedPassword = data.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(KEY_MD5);
        }
        catch (Exception e) {
            return null;
        }
        md.reset();
        md.update(unencodedPassword);
        final byte[] encodedPassword = md.digest();
        return encodedPassword;
    }

	/**
	 * SHA 加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] getSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();
	}
	
	/**
	 * SHA1 加密
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static byte[] getSha1(String src) throws Exception {
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(src.getBytes("UTF-8"));
		return crypt.digest();
	}
}