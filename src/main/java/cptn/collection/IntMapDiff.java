package cptn.collection;

public class IntMapDiff<V> extends MapDiff<Integer> {

	@Override
	protected boolean isEqual(Integer k1, Integer k2) {
		return k1 == k2;
	}
}
