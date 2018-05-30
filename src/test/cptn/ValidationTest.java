package cptn;

import cptn.validation.FieldValidator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ValidationTest {

	@Test
	public void testTel() {
		assertTrue(FieldValidator.INSTANCE.isMobile("13012345678"));
		assertTrue(FieldValidator.INSTANCE.isMobile("15899999999"));
		assertTrue(FieldValidator.INSTANCE.isMobile("18888888888"));
		assertTrue(!FieldValidator.INSTANCE.isMobile(null));
		assertTrue(!FieldValidator.INSTANCE.isMobile(""));
		assertTrue(!FieldValidator.INSTANCE.isMobile("130123456789"));
		assertTrue(!FieldValidator.INSTANCE.isMobile("1a0123456789"));
		assertTrue(!FieldValidator.INSTANCE.isMobile("1 012345678"));
		assertTrue(!FieldValidator.INSTANCE.isMobile("1-012345678"));
	}
}
