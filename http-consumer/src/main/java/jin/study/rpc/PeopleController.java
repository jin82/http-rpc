package jin.study.rpc;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 13:45
 * \* Description:
 * \
 */
@Component("peopleController")
public class PeopleController {

	@Resource
	private SpeakInterface speakInterface;

	public String getSpeak(String name,Integer age){
		People people = new People();
		people.setName(name);
		people.setAge(age);
		return speakInterface.speak(people);
	}
}
