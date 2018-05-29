package cptn.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 可包含多个数据的容器，常用作函数返回值
 * @author cptn
 *
 */
public class ProcessResult implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int CODE_OK = 0;
	public static final String DEFAULT_KEY = "_def_key_";

	// 返回的json对象属性
	public static String JK_CODE = "code";
	public static String JK_MSG = "msg";


	private int code;
	private Exception exception;
	private List<String> msgs;
	private Map<String, Object> datas;

	public ProcessResult() {
		code = CODE_OK;
		
		msgs = new ArrayList<String>();
		datas = new HashMap<String, Object>();
	}
	
	public boolean isOK() {
		return (code == CODE_OK);
	}
	
	public void setOK() {
		code = CODE_OK;
	}
	
	public void setErr() {
		code = -1;
	}

	public void setErr(int code) {
		this.code = code;
	}

	public void setErr(int code, String msg) {
		this.code = code;
		this.addMsg(msg);
	}

	public void setErr(Exception ex) {
		setErr();

		if (ex != null) {
			this.exception = ex;
			this.addMsg(ex.getMessage());
		}
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public final List<String> getMsgs() {
		return msgs;
	}
	
	/**
	 * 返回第一条消息
	 * @return
	 */
	public String getFirstMsg() {
		if (msgs == null) {
			return "";
		}
		
		if (msgs.size() == 0) {
			return "";
		}
		
		return msgs.get(0);
	}

	public final Map<String, Object> getDatas() {
		return datas;
	}
	
	public void addMsg(String msg) {
		if (msg == null) {
			return;
		}
		
		msgs.add(msg);
	}
	
	public void putData(String key, Object value) {
		if (StrUtil.INSTANCE.isEmpty2(key)) {
			return;
		}
		
		datas.put(key, value);
	}
	
	public Object getData(String key) {
		if (StrUtil.INSTANCE.isEmpty2(key)) {
			return null;
		}
		
		return datas.get(key);
	}
	
	public void putDefData(Object value) {
		putData(DEFAULT_KEY, value);
	}
	
	public Object getDefData() {
		return getData(DEFAULT_KEY);
	}
	
	public void clearData() {
		datas.clear();
	}
	
	public void clearMsg() {
		msgs.clear();
	}

	public String toJson() {
		if (isOK()) {
			return String.format("{\"%s\":%d}", JK_CODE, 0);
		}
		else {
			return String.format("{\"%s\":%d, \"%s\":\"%s\"}", JK_CODE, getCode(), JK_MSG, getFirstMsg());
		}
	}
}
