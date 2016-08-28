package jin.study.rpc.serialize;


import jin.study.rpc.exception.RpcException;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 11:23
 * \* Description: 将报文封装成对象
 * \
 */
public interface Parser {

	Request reqParse(String param) throws RpcException;

	<T> T rsbParse(String result);

}
