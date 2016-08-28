package jin.study.rpc.serialize.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import jin.study.rpc.serialize.Formater;
import jin.study.rpc.serialize.Request;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 11:30
 * \* Description: Json实现
 * \
 */
public class JsonFormater implements Formater {

	public static final Formater formater = new JsonFormater();

	@Override
	public String reqFormat(Class clazz, String method, Object param) {
		Request request = new Request();
		request.setClazz(clazz);
		request.setMethod(method);
		request.setParam(param);
		String a =  JSON.toJSONString(request, SerializerFeature.WriteClassName);
		return  a;
	}

	@Override
	public String rsbFormat(Object param) {
		return JSON.toJSONString(param, SerializerFeature.WriteClassName);
	}
}
