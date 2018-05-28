package cptn.encryption;

/**
 * 加/解密基本接口
 * @author cptn
 *
 */
public interface Cipher {
	void setAlgorithm(String al);
	String getAlgortithm();
	
	void setKey(String key);
	String getKey();
	
	int getKeyLength();
	void setKeyLength(int keyLength);
}
