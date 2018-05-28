package cptn.encryption;

public abstract class BaseCoder implements Cipher {
	private String al;
	private String key;
	private int keyLength;

	public BaseCoder(String al, String key) {
		setAlgorithm(al);
		setKey(key);
	}
	
	@Override
	public void setAlgorithm(String al) {
		this.al = al;
	}

	@Override
	public String getAlgortithm() {
		return al;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public int getKeyLength() {
		return keyLength;
	}

	@Override
	public void setKeyLength(int keyLength) {
		this.keyLength = keyLength;
	}

	
	protected byte[] buildKeyData(byte[] data, int len) {
		byte[] key = new byte[len]; // 声明一个24位的字节数组，默认里面都是0

		if (key.length > data.length) {
			System.arraycopy(data, 0, key, 0, data.length);
		} else {
			System.arraycopy(data, 0, key, 0, key.length);
		}

		return key;
	}
}
