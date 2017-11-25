package factory.abstractFactory.serviceImpl;

import factory.abstractFactory.iservice.AbstractFactory;
import factory.abstractFactory.iservice.ProductA;
import factory.abstractFactory.iservice.ProductB;

/**
 * @author Administrator
 * 工厂One实例化，可生产产品A和产品B
 */
public class ConcreateFactoryOne extends AbstractFactory{
	
	private ConcreateFactoryOne(){}
	private static final ConcreateFactoryOne concreateFactoryOne = new ConcreateFactoryOne();
	public static ConcreateFactoryOne getInstance(){
		return concreateFactoryOne;
	}
	
	@Override
	public ProductA FactoryOne() {
		return new ConcreateProductOneA();
	}

	@Override
	public ProductB FactoryTwo() {
		return new ConcreateProductOneB();
	}
}