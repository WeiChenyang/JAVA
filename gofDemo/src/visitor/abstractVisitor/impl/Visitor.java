package visitor.abstractVisitor.impl;

import visitor.abstractVisitor.interfaces.IVisitor;

/**
 * @author Administrator
 * 访问者实体化
 */
public class Visitor implements IVisitor {

	@Override
	public void visit(ConcreteElement1 elements1) {
		elements1.doSomthing();
	}
	@Override
	public void visit(ConcreteElement2 elements2) {
		elements2.doSomthing();
	}
}