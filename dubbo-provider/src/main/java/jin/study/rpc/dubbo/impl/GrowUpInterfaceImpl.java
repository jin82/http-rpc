package jin.study.rpc.dubbo.impl;

import jin.study.rpc.GrowUpInterface;
import jin.study.rpc.People;
import org.springframework.stereotype.Component;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/31
 * \* Time: 14:16
 * \* Description:
 * \
 */
@Component("growUpInterface")
public class GrowUpInterfaceImpl implements GrowUpInterface {
	public People addAge(People people) {
		people.setAge(people.getAge() + 1);
		return people;
	}
}
