package factory.simpleFactory;

import factory.simpleFactory.iservice.IFactory;
import factory.simpleFactory.serviceImpl.Car;
import factory.simpleFactory.serviceImpl.Plane;

public class SimpleFactory {
	
	public Object create(String productName) {  
		IFactory product = null;
		switch(productName){
		case "plane":
			product = new Plane();
			break;
		case "car":
			product = new Car();
			break;
		default:
			break;
		}
		return product;
	}  
}
