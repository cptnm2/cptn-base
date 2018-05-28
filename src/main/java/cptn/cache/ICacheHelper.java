package cptn.cache;

public interface ICacheHelper {
	public Object get(String key);
	public boolean put(String key, Object value, int dura);
}
