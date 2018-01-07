package bridge.impl;

import bridge.interfaces.Clothing;
import bridge.interfaces.Person;

/**
 * @author Administrator
 * ÄÐÈË
 */
public class Man extends Person{

	public Man() {
		setType("ÄÐÈË");
	}

	//´©ÒÂ·þ
	@Override
	public void dress() {
		Clothing clothing = getClothing();
		clothing.personDressCLoth(this);
	}

	
}