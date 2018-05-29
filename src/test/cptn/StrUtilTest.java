package cptn;

import cptn.util.StrUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class StrUtilTest {
	@Test
	public void testDecimal() {
		String value = "10101";
		assertTrue(StrUtil.INSTANCE.safeParseDecimal(value).intValue() == 10101);
		
		value = "-1";
		assertTrue(StrUtil.INSTANCE.safeParseDecimal(value).intValue() == -1);
		
		value = "dna";
		assertTrue(StrUtil.INSTANCE.safeParseDecimal(value).intValue() == 0);
	}

	@Test
	public void testHexStr() {
		byte[] data = {1, 2, 10, 22};

		System.out.println(StrUtil.INSTANCE.byteArrayToHexStr(data));
	}
	
	@Test
	public void testChinese() {
//		System.out.println("转换中文金额");
//		System.out.println("-------------------------");
//		System.out.println("25000000000005.999: " + ChineseMoney.toChinese(25000000000005.999));
//		System.out.println("45689263.626: " + ChineseMoney.toChinese(45689263.626));
//		System.out.println("0.69457: " + ChineseMoney.toChinese(0.69457));
//		System.out.println("250.0: " + ChineseMoney.toChinese(250.0));
//		System.out.println("0: " + ChineseMoney.toChinese(0));
//		System.out.println("-------------------------");
	}
	
	@Test
	public void testDecodeUtf() throws UnsupportedEncodingException {
		String src = "%E8%B5%B5";
		
		String dst = java.net.URLDecoder.decode(src, "UTF-8");
		
		System.out.println(dst);
	}
	
	@Test
	public void testPasswordLevel() {
		assertEquals(StrUtil.INSTANCE.getPasswordLevel(null, true), 1);
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("", true), 1);
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("   ", true), 1);
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("123", true), 1);
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("ddd", true), 1);
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("ACD", true), 1);
		
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("ACDSes", true), 2);
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("ACDSes", false), 1);
		
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("ACDSes3", true), 3);
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("ACDSes_3", true), 4);
		
		assertEquals(StrUtil.INSTANCE.getPasswordLevel("ACDSes_3", false), 3);
	}
}
