package bridge.impl;

import bridge.interfaces.Clothing;
import bridge.interfaces.Person;

/**
 * @author Administrator
 * 定义穿裤子类
 */
public class Trouser extends Clothing {  

	@Override
	public void personDressCLoth(Person person) {
		System.out.println(person.getType() + "穿裤子");  
	}  
}  