package visitor.abstractVisitor.impl;

import visitor.abstractVisitor.interfaces.Elements;
import visitor.abstractVisitor.interfaces.IVisitor;

/**
 * @author Administrator
 * 元素类1实体化
 */
public class ConcreteElement1 extends Elements{

	@Override
	public void doSomthing() {
		System.out.println("这是元素1");
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}