package jin.study.rpc.invoke;

import jin.study.rpc.zookeeper.ZookeeperClient;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 12:28
 * \* Description: 生产者配置
 * \
 */
public class ProviderConfig {

	private String target;

	private Integer port;

	private ZookeeperClient zkClient;

	public ProviderConfig() {
	}

	public ProviderConfig(String target, Integer port) {
		this.target = target;
		this.port = port;
		if(target.toLowerCase().startsWith("zookeeper://")){
			zkClient = new ZookeeperClient(target.toLowerCase().replaceFirst("zookeeper://", ""));
		}
	}

	public void register(Class clazz) {
		if (zkClient != null) {
			zkClient.createPersistent("/rpc/" + clazz.getName().replaceAll("\\.", "/"));
			zkClient.createEphemeral("/rpc/" + clazz.getName().replaceAll("\\.", "/") + "/node", getNodeInfo());
		}
	}

	public String getNodeInfo() {
		try {
			return "http://"+Inet4Address.getLocalHost().getHostAddress()+":"+getPort();
		}catch (UnknownHostException e){
			e.printStackTrace();
		}
		return null;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
