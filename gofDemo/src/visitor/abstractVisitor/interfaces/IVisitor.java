package visitor.abstractVisitor.interfaces;

import visitor.abstractVisitor.impl.ConcreteElement1;
import visitor.abstractVisitor.impl.ConcreteElement2;

/**
 * @author Administrator
 * 抽象访问者   接口
 */
public interface IVisitor {
	
	public void visit(ConcreteElement1 elements);
	public void visit(ConcreteElement2 elements);
}