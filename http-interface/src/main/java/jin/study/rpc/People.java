package jin.study.rpc;

import java.io.Serializable;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/08/28
 * \* Time: 13:41
 * \* Description:
 * \
 */
public class People implements Serializable{

	private String name;

	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
