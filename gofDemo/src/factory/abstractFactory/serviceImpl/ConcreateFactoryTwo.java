package factory.abstractFactory.serviceImpl;

import factory.abstractFactory.iservice.AbstractFactory;
import factory.abstractFactory.iservice.ProductA;
import factory.abstractFactory.iservice.ProductB;

/**
 * @author Administrator
 * 实例化工厂Two，可生产产品A 和产品B
 */
public class ConcreateFactoryTwo extends AbstractFactory{

	private ConcreateFactoryTwo(){}
	private static final ConcreateFactoryTwo concreateFactoryTwo = new ConcreateFactoryTwo();
	public static ConcreateFactoryTwo getInstance(){
		return concreateFactoryTwo;
	}
	@Override
	public ProductA FactoryOne() {
		return new ConcreateProductTwoA();
	}
	@Override
	public ProductB FactoryTwo() {
		return new ConcreateProductTwoB();
	}
}