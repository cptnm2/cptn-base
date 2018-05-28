package cptn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {

	/**
	 * 从流中读取字符串
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static StringBuffer readTextStream(InputStream is) throws IOException {
		StringBuffer buff = new StringBuffer();
		BufferedReader reader = null;
        String line;
		
		try {
			reader = new BufferedReader(new InputStreamReader(is));
	        while ((line = reader.readLine()) != null) {
	        	buff.append(line);
	        }		
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
		
		return buff;
	}
}
