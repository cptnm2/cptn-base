package cptn.encryption;

import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * AES 加密/解密工具
 * @author cptn
 *
 */
public abstract class AesCoder extends BaseCoder {

	// 模式
	public static final String DEF_ENC_ALGORITHM = "AES/ECB/PKCS5Padding";
	public static final String DEF_DEC_ALGORITHM = "AES/ECB/NoPadding";

	// 算法
	public static final String KEY_ALGORITHM = "AES";

	// 加密位数
	public static final int KEY_LEN_128 = 128;
	public static final int KEY_LEN_256 = 256;
	
	public AesCoder(String al, String key, int keyLength) {
		super(al, key);
		setKeyLength(keyLength);
	}

	/**
	 * 构建密钥
	 * @return
	 * @throws Exception
	 */
	protected Key buildKey() throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
        
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(getKey().getBytes());

        kgen.init(getKeyLength(), random);
        SecretKey key = kgen.generateKey();

        return key;
	}

	@Override
	public void setKeyLength(int keyLength) {
		if (keyLength == KEY_LEN_128 || keyLength == KEY_LEN_256) {
			super.setKeyLength(keyLength);
		}
		else {
			throw new InvalidParameterException();
		}
	}
}
