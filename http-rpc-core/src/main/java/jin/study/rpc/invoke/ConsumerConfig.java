package jin.study.rpc.invoke;

import jin.study.rpc.exception.RpcException;
import jin.study.rpc.zookeeper.ZookeeperClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 12:28
 * \* Description: 消费者配置
 * \
 */
public class ConsumerConfig {

	private String url;

	private ZookeeperClient zkClient;

	private final ConcurrentHashMap<Class, AtomicInteger> invokeCount = new ConcurrentHashMap<>();

	public String getUrl(Class clazz) throws RpcException{
		if (zkClient != null) {
			List<String> urlList = new ArrayList<>();
			List<String> pathList = zkClient.getChildren("/rpc/"+clazz.getName().replaceAll("\\.","/"));
			for (String path : pathList) {
				String httpUrl = zkClient.getData("/rpc/"+clazz.getName().replaceAll("\\.","/")+"/"+path);
				if (httpUrl != null) {
					urlList.add(httpUrl);
				}
			}
			return getCurrentUrl(clazz, urlList);
		}else{
			return url;
		}
	}

	public String getCurrentUrl(Class clazz,List<String> urlList) throws RpcException {
		if (invokeCount.get(clazz) == null) {
			invokeCount.putIfAbsent(clazz, new AtomicInteger(1));
			return urlList.get(0);
		}else{
			int i = invokeCount.get(clazz).incrementAndGet();
			return urlList.get(i%urlList.size());
		}
	}

	public void setUrl(String url) {
		this.url = url;
		if (url.toLowerCase().startsWith("zookeeper://")) {
			zkClient = new ZookeeperClient(url.toLowerCase().replaceFirst("zookeeper://", ""));
		}
	}
}
