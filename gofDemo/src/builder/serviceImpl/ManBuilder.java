package builder.serviceImpl;

import builder.iService.PersonBuilder;
import builder.vo.Person;

/**
 * @author Administrator
 * person对象构造器
 */
public class ManBuilder implements PersonBuilder {

	private Person person;
	
	public ManBuilder(Person person) {
		//构造人
		this.person = person;
	}

	@Override
	public void buildHead() {
		person.setHead("造头");
	}

	@Override
	public void buildBody() {
		person.setBody("造身体");
	}

	@Override
	public void buildFoot() {
		person.setFoot("造脚");
	}

	@Override
	public Person buildPerson() {
		return person;
	}

}
