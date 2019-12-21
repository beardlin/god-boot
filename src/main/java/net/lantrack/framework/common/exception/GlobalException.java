
package net.lantrack.framework.common.exception;
/**
 *@Description 统一抛出异常
 *@Author dahuzi
 *@Date 2019/10/28  21:43
 */
public class GlobalException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public GlobalException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public GlobalException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public GlobalException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public GlobalException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


}
