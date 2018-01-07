package bridge.impl;

import bridge.interfaces.Clothing;
import bridge.interfaces.Person;

/**
 * @author Administrator
 * 定义穿马甲类
 */
public class Jacket extends Clothing{

	@Override
	public void personDressCLoth(Person person) {
		System.out.println(person.getType() + "穿马甲");
	}
}
