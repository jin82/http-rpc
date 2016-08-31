package test;

import jin.study.rpc.People;
import jin.study.rpc.SpeakInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-dubbo-consumer.xml","classpath:spring-context.xml"})
//@Ignore
public class HttpRpcTest {
	@Resource
	private SpeakInterface speakInterface;

	@Test
	public void test() throws InterruptedException {
//		while (true)
//		{
			People people =   new People();
			people.setAge(18);
			people.setName("King");
			System.out.println(speakInterface.speak(people));
//			Thread.sleep(new Random().nextInt(10));
//		}
	}
}