package cptn.collection;

import cptn.util.StrUtil;

public class StrMapDiff<V> extends MapDiff<String> {

	@Override
	protected boolean isEqual(String k1, String k2) {
		return StrUtil.INSTANCE.isEqual(k1, k2);
	}
}
