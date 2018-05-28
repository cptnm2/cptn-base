package cptn.util;

/**
 * A class to replace Map.Entry
 * @author cptn
 *
 * @param <K>
 * @param <V>
 */
public class KeyValue<K, V> {

	private K key;
	private V value;

	public KeyValue() {
	}
	
	public KeyValue(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	public String toString() {
		return objToString(key) + ":" + objToString(value);
	}
	
	private String objToString(Object obj) {
		if (obj == null) {
			return "(NULL)";
		}
		else if (obj instanceof String) {
			return (String) obj;
		}
		else {
			return obj.toString();
		}
	}

}
