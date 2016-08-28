package jin.study.rpc.container;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 12:56
 * \* Description:
 * \
 */
public class Main {
	public Main() {
	}

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
		context.start();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		countDownLatch.await();
	}

}
