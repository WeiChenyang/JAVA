package factory.abstractFactory.serviceImpl;

import factory.abstractFactory.iservice.ProductA;

/**
 * @author Administrator
 * 工厂Two生产产品A
 */
public class ConcreateProductTwoA implements ProductA{

	@Override
	public void method1() {
		System.out.println("Two厂生产的产品A method1()");
	}

	@Override
	public void method2() {
		System.out.println("Two厂生产的产品A method2()");
	}
}
