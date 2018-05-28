package cptn.encryption;

import java.security.Key;

import javax.crypto.Cipher;

/**
 * AES 加密/解密工具
 * @author cptn
 *
 */
public class AesDecoder extends AesCoder implements Decrypt {
	
	public AesDecoder(String key, int keyLen) {
		super(DEF_DEC_ALGORITHM, key, keyLen); // 默认模式
	}

	public AesDecoder(String al, String key, int keyLen) {
		super(al, key, keyLen);
	}

	@Override
	public byte[] decode(byte[] data) throws Exception {
		Key sk = buildKey();

		//实例化  
        Cipher cipher = Cipher.getInstance(getAlgortithm());  

        //使用密钥初始化，设置为解密模式  
        cipher.init(Cipher.DECRYPT_MODE, sk);  
        
        //执行操作  
        return cipher.doFinal(data);
    }
}
