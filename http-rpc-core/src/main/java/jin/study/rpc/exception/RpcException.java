package jin.study.rpc.exception;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 12:10
 * \* Description:
 * \
 */
public class RpcException extends Throwable {
	private String code;

	private Object data;

	public RpcException(String message, Throwable cause, String code, Object data) {
		super(message, cause);
		this.code = code;
		this.data = data;
	}

	public RpcException(String code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}
}
