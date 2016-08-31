package jin.study.rpc.dubbo.impl;

import jin.study.rpc.GrowUpInterface;
import jin.study.rpc.People;
import jin.study.rpc.SpeakInterface;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/31
 * \* Time: 13:54
 * \* Description:
 * \
 */
@Component("speakInterface")
public class SpeakInterfaceImpl implements SpeakInterface{

	@Resource
	private GrowUpInterface growUpInterface;

	@Override
	public String speak(People people) {
		people = growUpInterface.addAge(people);
		return "My age is " + people.getAge();
	}
}
