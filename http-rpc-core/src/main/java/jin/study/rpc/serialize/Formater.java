package jin.study.rpc.serialize;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 11:17
 * \* Description: 将对象封装成报文
 * \
 */
public interface Formater {

	/**
	 * 将请求对象封装成请求报文
	 * @param clazz 请求的接口
	 * @param method 请求的方法
	 * @param param 请求的参数
	 * @return
	 */
	String reqFormat(Class clazz, String method, Object param);

	/**
	 * 将响应的对象封装成响应的报文
	 * @param param 响应的结果
	 * @return
	 */
	String rsbFormat(Object param);
}
