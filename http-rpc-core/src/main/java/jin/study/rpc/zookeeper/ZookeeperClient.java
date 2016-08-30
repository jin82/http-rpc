package jin.study.rpc.zookeeper;

import jin.study.rpc.exception.RpcException;
import jin.study.rpc.exception.RpcExceptionCodeEnum;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/30
 * \* Time: 16:07
 * \* Description: zookeeper 客户端
 * \
 */
public class ZookeeperClient {

	private ZkClient zkClient;

	private volatile Watcher.Event.KeeperState state = Watcher.Event.KeeperState.SyncConnected;

	public ZookeeperClient(String url) {
		this.zkClient = new ZkClient(url);
	}

	//创建持久化目录
	public void createPersistent(String path){
		try {
			zkClient.createPersistent(path,true);
		}catch (Exception e){
			//logger.error("创建 {} 持久化目录出错",path);
			e.printStackTrace();
		}
	}

	//创建临时目录
	public void createEphemeral(String path,String data) {
		try{
			zkClient.createEphemeralSequential(path,data);
		}catch (Exception e){
			//logger.error("创建 {} 临时目录出错",path);
			e.printStackTrace();
		}
	}

	//删除临时目录
	public void deleteEphemeral(String path){
		try {
			zkClient.delete(path);
		} catch (Exception e) {
			//logger.error("删除 {} 临时目录出错",path);
			e.printStackTrace();
		}
	}

	//获取子目录
	public List<String> getChildren(String path) throws RpcException {
		try {
			List<String> pathList = zkClient.getChildren(path);
			if(pathList != null && pathList.size() > 0) {
				return pathList;
			}
		}catch (ZkNodeExistsException e){
			throw new RpcException(e.getMessage(),e,RpcExceptionCodeEnum.NO_PROVIDERS.getCode(), path);
		}
		throw new RpcException(RpcExceptionCodeEnum.NO_PROVIDERS.getCode(), path);
	}

	public <T> T getData(String path){
		try {
			return zkClient.readData(path);
		} catch (ZkNoNodeException e) {
			return null;
		}
	}

	public void delete(String path){
		try {
			zkClient.delete(path);
		} catch (Exception e) {
			//logger.error("删除 {} 目录出错",path);
			e.printStackTrace();
		}
	}

	public void setWatcher(String path, IZkChildListener watcher) {
		zkClient.subscribeChildChanges(path, watcher);
	}

	public boolean isConnected() {
		return state == Watcher.Event.KeeperState.SyncConnected;
	}

	public void doClose() {
		zkClient.close();
	}

}

