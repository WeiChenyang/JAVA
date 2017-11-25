package prototype.serviceImpl;

import prototype.iservice.Prototype;

/**
 * @author Administrator
 * 具体原型角色2
 */
public class ConcreatePrototype2 implements Prototype{

	private String name;
	
	@Override
	public Prototype clone() {
		ConcreatePrototype2 prototype = new ConcreatePrototype2();
		prototype.setName(this.name);
		return prototype;
	}

	public String toString(){
        return "Now in Prototype2 , name = " + this.name;
    }
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}