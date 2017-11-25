package factory.simpleFactory.serviceImpl;

import factory.simpleFactory.iservice.IFactory;

public class Plane implements IFactory{

	@Override
	public void create() {
		System.out.println("Create Plane!");
	}
}