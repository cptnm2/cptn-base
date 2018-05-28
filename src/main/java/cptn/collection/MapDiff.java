package cptn.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 比较两个Map，寻找各自独有的值
 * @author cptn
 *
 * @param <K>
 * @param <V>
 */
public abstract class MapDiff<K> {

	private List<K> r1; // map1 中特有的值
	private List<K> r2; // map2 中特有的值
	
	public MapDiff() {
		r1 = new ArrayList<K>();
		r2 = new ArrayList<K>();
	}
	
	public void diff(Map<K, ?> map1, Map<K, ?> map2) {
		findUniqueSrc(map1, map2, r1);
		findUniqueSrc(map2, map1, r2);
	}
	
	protected void findUniqueSrc(Map<K, ?> src, Map<K, ?> dest, List<K> result) {
		result.clear();
		
		for (Iterator<K> iter = src.keySet().iterator(); iter.hasNext(); ) {
			K k = iter.next();
			
			if (dest.containsKey(k)) { // 两个Map共有的
				continue;
			}
			else {
				result.add(k); // 特有值存放在结果集
			}
		}
	}

	protected abstract boolean isEqual(K k1, K k2);
	
	public List<K> getResult1() {
		return r1;
	}

	public List<K> getResult2() {
		return r2;
	}
}
