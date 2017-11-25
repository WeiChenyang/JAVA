package prototype.serviceImpl;

import prototype.iservice.Prototype;

/**
 * @author Administrator
 * 具体原型角色1
 */
public class ConcreatePrototype1 implements Prototype{

	private String name;
	
	@Override
	public Prototype clone() {
		ConcreatePrototype1 prototype = new ConcreatePrototype1();
		prototype.setName(this.name);
		
		return prototype;
	}

	public String toString(){
        return "Now in Prototype1 , name = " + this.name;
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