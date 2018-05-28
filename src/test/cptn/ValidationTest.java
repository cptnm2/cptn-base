package cptn;

import cptn.validation.FieldValidator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ValidationTest {

	@Test
	public void testTel() {
		assertTrue(FieldValidator.isMobile("13012345678"));
		assertTrue(FieldValidator.isMobile("15899999999"));
		assertTrue(FieldValidator.isMobile("18888888888"));
		assertTrue(!FieldValidator.isMobile(null));
		assertTrue(!FieldValidator.isMobile(""));
		assertTrue(!FieldValidator.isMobile("130123456789"));
		assertTrue(!FieldValidator.isMobile("1a0123456789"));
		assertTrue(!FieldValidator.isMobile("1 012345678"));
		assertTrue(!FieldValidator.isMobile("1-012345678"));
	}
}
