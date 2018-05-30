package cptn;

import cptn.util.FileUtil;
import org.junit.Test;

import static org.junit.Assert.fail;

public class FileUtilTest {

	@Test
	public void testGetFilePathName() {
		testGetFilePathName("E:\\Temp\\test.ftl", new String[] { "E:\\Temp\\", "test.ftl" });
		testGetFilePathName("E:\\Temp\\", new String[] { "E:\\Temp\\", "" });
		testGetFilePathName("test.ftl", new String[] { "", "test.ftl" });
		testGetFilePathName(null, new String[] { "", "" });
	}
	
	private void testGetFilePathName(String src, String[] expect) {
		String[] data = FileUtil.INSTANCE.getFilePathName(src);
		
		if ((!data[0].equalsIgnoreCase(expect[0])) || (!data[1].equalsIgnoreCase(expect[1]))) {
			fail();
		}
	}
	
	@Test
	public void testAppend() {
		String u1 = "http://localhost";
		String u2 = "/zsgame/index";
		
		System.out.println(FileUtil.INSTANCE.appendFileName(u1, u2));
	}
}
