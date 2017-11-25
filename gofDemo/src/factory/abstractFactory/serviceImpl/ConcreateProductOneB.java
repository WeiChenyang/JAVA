package factory.abstractFactory.serviceImpl;

import factory.abstractFactory.iservice.ProductB;

/**
 * @author Administrator
 * 工厂One生产产品B
 */
public class ConcreateProductOneB implements ProductB{

	@Override
	public void method1() {
		System.out.println("One厂生产的产品B method1()");
	}

	@Override
	public void method2() {
		System.out.println("One厂生产的产品B method2()");
	}
}