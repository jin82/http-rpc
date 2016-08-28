package jin.study.rpc.serialize;

import java.io.Serializable;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 11:26
 * \* Description:
 * \
 */
public class Request implements Serializable{

	private Class clazz;
	private String method;
	private Object param;

	public Request() {
	}

	public Class getClazz() {
		return this.clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Object getParam() {
		return this.param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public Object invoke(Object bean) throws Exception {
		return this.clazz.getMethod(this.method, new Class[]{this.param.getClass()}).invoke(bean, new Object[]{this.param});
	}
}
