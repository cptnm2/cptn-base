package cptn.util;

public class ThreadUtil {
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 取得当前的堆栈调用
	 * @return
	 */
	public static String getCurStackTrace() {
		StringBuffer buff = new StringBuffer();
		
		StackTraceElement[] st = Thread.currentThread().getStackTrace();
		for (int i = 2; i < st.length; i++) {
			buff.append(st[i].toString());
			buff.append(System.lineSeparator());
		}
		return buff.toString();
	}

	/**
	 * 将异常的错误信息转换成字符串
	 * @param e
	 * @return
	 */
	public static String getStackTraceStr(Exception e) {
		if (e == null) {
			return null;
		}

		StringBuffer buff = new StringBuffer();

		StackTraceElement[] st = e.getStackTrace();
		for (int i = 2; i < st.length; i++) {
			buff.append(st[i].toString());
			buff.append(System.lineSeparator());
		}

		return buff.toString();
	}
}
