package jin.study.rpc;

import org.springframework.stereotype.Component;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 14:13
 * \* Description:
 * \
 */
@Component("speakInterface")
public class SpeakInterfaceImpl implements  SpeakInterface {
	@Override
	public String speak(People people) {
		System.out.println("name is "+people.getName()+",age is "+people.getAge());
		return people.getName();
	}
}
