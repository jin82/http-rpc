package jin.study.rpc.serialize.json;


import com.alibaba.fastjson.JSON;
import jin.study.rpc.exception.RpcException;
import jin.study.rpc.exception.RpcExceptionCodeEnum;
import jin.study.rpc.serialize.Parser;
import jin.study.rpc.serialize.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 11:34
 * \* Description: Json实现
 * \
 */
public class JsonParser implements Parser {

	public static final Parser parser = new JsonParser();

	private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

	@Override
	public Request reqParse(String param) throws RpcException {

		try {
			logger.debug("调用参数 {" + param + "}");
			return (Request) JSON.parse(param);
		}catch (Exception e){
			logger.error("转换异常 param = {" + param + "}",e);
			throw new RpcException("",e, RpcExceptionCodeEnum.DATA_PARSER_ERROR.getCode(),param);
		}


	}

	@Override
	public <T> T rsbParse(String result) {
		return (T)JSON.parse(result);
	}
}
