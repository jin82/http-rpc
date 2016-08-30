package jin.study.rpc.proxy;

import jin.study.rpc.exception.RpcException;
import jin.study.rpc.invoke.ConsumerConfig;
import jin.study.rpc.invoke.HttpInvoker;
import jin.study.rpc.invoke.Invoker;
import jin.study.rpc.serialize.Formater;
import jin.study.rpc.serialize.Parser;
import jin.study.rpc.serialize.json.JsonFormater;
import jin.study.rpc.serialize.json.JsonParser;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 13:15
 * \* Description:
 * \
 */
public class ConsumerProxyFactory implements InvocationHandler {

	private ConsumerConfig consumerConfig;

	private Parser parser = JsonParser.parser;

	private Formater formater = JsonFormater.formater;

	private Invoker invoker = HttpInvoker.invoker;

	private String clazz;

	public Object create() throws Exception{
		Class interfaceClass = Class.forName(clazz);
		return Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class[]{interfaceClass},this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Class interfaceClass = proxy.getClass().getInterfaces()[0];
		String req = formater.reqFormat(interfaceClass,method.getName(),args[0]);
		String resb = null;
		int times = 0 ;
		while (times++ < 2 && resb == null) {
			try{
				resb = invoker.request(req, consumerConfig.getUrl(interfaceClass));
			}catch (RpcException e){
				e.printStackTrace();
			}
		}
		if (resb == null) {
			invoker.request(req, consumerConfig.getUrl(interfaceClass));
		}
		return parser.rsbParse(resb);
	}

	public ConsumerConfig getConsumerConfig() {
		return consumerConfig;
	}

	public void setConsumerConfig(ConsumerConfig consumerConfig) {
		this.consumerConfig = consumerConfig;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public Formater getFormater() {
		return formater;
	}

	public void setFormater(Formater formater) {
		this.formater = formater;
	}

	public Invoker getInvoker() {
		return invoker;
	}

	public void setInvoker(Invoker invoker) {
		this.invoker = invoker;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
