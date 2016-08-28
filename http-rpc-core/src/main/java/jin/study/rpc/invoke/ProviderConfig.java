package jin.study.rpc.invoke;

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

	public ProviderConfig() {
	}

	public ProviderConfig(String target, Integer port) {
		this.target = target;
		this.port = port;
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
