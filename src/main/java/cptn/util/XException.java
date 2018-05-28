package cptn.util;

/**
 * 自定义异常处理
 * @author cptn
 *
 */
public class XException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int errCode = 0;
	
	public XException() {
		super();
	}

	public XException(String msg) {
		super(msg);
	}
	
	public XException(int err, String msg) {
		super(msg);
		errCode = err;
	}

	public int getErrCode() {
		return errCode;
	}
	
	@Override
	public String toString() {
		return errCode + ": " + getMessage();
	}
}
