package jin.study.rpc.proxy;

import jin.study.rpc.container.Container;
import jin.study.rpc.container.HttpContainer;
import jin.study.rpc.exception.RpcException;
import jin.study.rpc.exception.RpcExceptionCodeEnum;
import jin.study.rpc.invoke.HttpInvoker;
import jin.study.rpc.invoke.Invoker;
import jin.study.rpc.invoke.ProviderConfig;
import jin.study.rpc.serialize.Formater;
import jin.study.rpc.serialize.Parser;
import jin.study.rpc.serialize.Request;
import jin.study.rpc.serialize.json.JsonFormater;
import jin.study.rpc.serialize.json.JsonParser;
import org.mortbay.jetty.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 12:20
 * \* Description: 生产者代理工厂
 * \
 */
public class ProviderProxyFactory extends AbstractHandler{

	private static final Logger logger = LoggerFactory.getLogger(ProviderProxyFactory.class);

	private Map<Class,Object> providers = new ConcurrentHashMap<>();

	private static ProviderProxyFactory factory;

	private Parser parser = JsonParser.parser;

	private Formater formater = JsonFormater.formater;

	private Invoker invoker = HttpInvoker.invoker;

	public ProviderProxyFactory(Map<Class, Object> providers) {
		if(Container.container == null){
			new HttpContainer(this).start();
		}
		for(Map.Entry<Class,Object> entry : providers.entrySet()){
			register(entry.getKey(),entry.getValue());
		}
		factory = this;
	}

	public ProviderProxyFactory(Map<Class,Object> providers, ProviderConfig providerConfig){
		if(Container.container == null){
			new HttpContainer(this,providerConfig).start();
		}
		for(Map.Entry<Class,Object> entry : providers.entrySet()){
			register(entry.getKey(),entry.getValue());
		}
		factory = this;
	}

	public void register(Class clazz,Object object){
		providers.put(clazz, object);
		logger.info(clazz.getSimpleName() + " 已经发布");
	}

	public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int i) throws IOException, ServletException {
		String reqStr = httpServletRequest.getParameter("data");
		try{
			Request rpcRequest = parser.reqParse(reqStr);
			Object result = rpcRequest.invoke(getInstance().getBeanByClass(rpcRequest.getClass()));
			invoker.response(formater.rsbFormat(result), httpServletResponse.getOutputStream());
		}catch (RpcException e){
			e.printStackTrace();
		}catch (Throwable e){
			e.printStackTrace();
		}
	}

	public Object getBeanByClass(Class clazz) throws RpcException{
		Object bean = providers.get(clazz);
		if(bean != null){
			return bean;
		}
		throw new RpcException(RpcExceptionCodeEnum.NO_BEAN_FOUND.getCode(),clazz);
	}

	public static ProviderProxyFactory getInstance(){
		return factory;
	}
}
