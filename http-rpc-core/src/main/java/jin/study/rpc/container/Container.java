package jin.study.rpc.container;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 12:46
 * \* Description:容器
 * \
 */
public abstract class Container {

	public static volatile boolean isStart = false;

	public abstract void start();

	public static volatile Container container = null;

}
