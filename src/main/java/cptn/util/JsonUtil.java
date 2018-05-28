package cptn.util;

import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class JsonUtil {

	/**
	 * 将json转换成map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> jsonToMap(String json) { 
		Gson gson = new Gson();
		TypeToken<LinkedHashMap<String, String>> tt = new TypeToken<LinkedHashMap<String, String>>(){ };
		
		Map<String, String> map = (Map<String, String>) gson.fromJson(json, tt.getType());
		return (map == null ? new LinkedHashMap<String, String>(): map);
	}
	
	/**
	 * 将map转换成json
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map<String, String> map) {
		if (map == null) {
			return "{}";
		}
		
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap2(String json) { 
		Gson gson = new Gson();
		TypeToken<LinkedHashMap<String, Object>> tt = new TypeToken<LinkedHashMap<String, Object>>(){ };
		
		Map<String, Object> map = (Map<String, Object>) gson.fromJson(json, tt.getType());
		return (map == null ? new LinkedHashMap<String, Object>(): map);
	}
	
	/**
	 * 将map转换成json
	 * @param map
	 * @return
	 */
	public static String mapToJson2(Map<String, Object> map) {
		if (map == null) {
			return "{}";
		}
		
		Gson gson = new Gson();
		return gson.toJson(map);
	}

	/**
	 * 将json转换为对象，允许不完整的数据
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T jsonToObj(String json, Class<T> classOfT) {
		Gson gson = new Gson();
		JsonReader jr = new JsonReader(new StringReader(json));
		jr.setLenient(true);
		return gson.fromJson(jr, classOfT);
	}

	/**
	 * 整形集合转换成json
	 * @param set
	 * @return
	 */
	public static String intSetToJson(Set<Integer> set) {
		if (set == null) {
			return "null";
		}

		StringBuffer  buff = new StringBuffer();
		boolean first = true;

		buff.append("[");
		Iterator<Integer> iter = set.iterator();
		while (iter.hasNext()) {
			int n = iter.next();

			if (first) {
				first = false;
			}
			else {
				buff.append(",");
			}

			buff.append(n);
		}
		buff.append("]");
		return buff.toString();
	}
}
