package jin.study.rpc.invoke;

import jin.study.rpc.exception.RpcException;
import jin.study.rpc.exception.RpcExceptionCodeEnum;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 12:27
 * \* Description: Http实现
 * \
 */
public class HttpInvoker implements Invoker {

	private static final HttpClient httpClient = getHttpClient();

	public static final Invoker invoker = new HttpInvoker();

	@Override
	public String request(String request, ConsumerConfig consumerConfig) throws RpcException {
		HttpPost post = new HttpPost(consumerConfig.getUrl());
		post.setHeader("Connection","Keep-Alive");
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("data",request));
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(post);
			if(response.getStatusLine().getStatusCode() == 200){
				return EntityUtils.toString(response.getEntity(),"UTF-8");
			}
			throw  new RpcException(RpcExceptionCodeEnum.INVOKE_REQUEST_ERROR.getCode(),request);
		}catch (IOException e){
			throw new RpcException("http 调用异常",e,RpcExceptionCodeEnum.INVOKE_REQUEST_ERROR.getCode(),request);
		}
	}

	@Override
	public void response(String response, OutputStream outputStream) throws RpcException {
		try {
			outputStream.write(response.getBytes("UTF-8"));
			outputStream.flush();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static HttpClient getHttpClient(){
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		//连接池最大生成连接数
		cm.setMaxTotal(200);
		//设置route最大连接数
		cm.setDefaultMaxPerRoute(20);
		//指定专门的route，设置最大连接数为80
		HttpHost localhost = new HttpHost("localhost",8888);
		cm.setMaxPerRoute(new HttpRoute(localhost),50);
		return HttpClients.custom().setConnectionManager(cm).build();
	}
}
