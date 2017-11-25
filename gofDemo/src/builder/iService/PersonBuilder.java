package builder.iService;

import builder.vo.Person;

/**
 * @author Administrator
 * Person对象的构造接口
 */
public interface PersonBuilder {
	public void buildHead();
	public void buildBody();
	public void buildFoot();
	public Person buildPerson();
}
