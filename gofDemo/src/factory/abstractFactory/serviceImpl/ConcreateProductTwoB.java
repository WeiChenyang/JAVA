package factory.abstractFactory.serviceImpl;

import factory.abstractFactory.iservice.ProductB;

/**
 * @author Administrator
 * 工厂Two生产产品B
 */
public class ConcreateProductTwoB implements ProductB{

	@Override
	public void method1() {
		System.out.println("Two厂生产的产品B method1()");
	}

	@Override
	public void method2() {
		System.out.println("Two厂生产的产品B method2()");
	}
}