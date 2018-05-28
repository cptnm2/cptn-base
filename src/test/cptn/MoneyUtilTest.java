package cptn;

import cptn.business.MoneyUtil;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class MoneyUtilTest {
	@Test
	public void testDecimal() {
		assertTrue(MoneyUtil.isEqual(null, null));
		assertTrue(MoneyUtil.isEqual(new BigDecimal("1.15"), new BigDecimal("1.15")));
		assertTrue(MoneyUtil.isEqual(new BigDecimal("1.15"), new BigDecimal("1.1500")));
		assertTrue(MoneyUtil.isEqual(new BigDecimal("1.15"), new BigDecimal("1.151")));

		
		assertTrue(!MoneyUtil.isEqual(null, new BigDecimal("1.15")));
		assertTrue(!MoneyUtil.isEqual(new BigDecimal("1.15"), null));
		assertTrue(!MoneyUtil.isEqual(new BigDecimal("1.155"), new BigDecimal("1.15")));
	}
}
