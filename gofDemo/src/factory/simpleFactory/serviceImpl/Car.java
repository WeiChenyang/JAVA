package factory.simpleFactory.serviceImpl;

import factory.simpleFactory.iservice.IFactory;

public class Car implements IFactory {
	@Override
	public void create() {
		System.out.println("Create Car!");
	}
}