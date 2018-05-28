package cptn.collection;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 集合类工具
 * @author cptn
 *
 */
public class CollectionUtil {
	/**
	 * 对列表进行排序
	 * @param list
	 */
	public static void sortList(List<String> list) {
		Collections.sort(list, new Comparator<String>() {
			public int compare(String a, String b) {
				return a.compareTo(b);
			}
		});
	}
}
