package visitor.abstractVisitor.impl;

import visitor.abstractVisitor.interfaces.Elements;
import visitor.abstractVisitor.interfaces.IVisitor;

/**
 * @author Administrator
 * 元素类2实体化
 */
public class ConcreteElement2 extends Elements{

	@Override
	public void doSomthing() {
		System.out.println("这是元素2");
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}