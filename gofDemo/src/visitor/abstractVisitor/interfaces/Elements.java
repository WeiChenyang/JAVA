package visitor.abstractVisitor.interfaces;

/**
 * @author Administrator
 * 抽象元素类  接口
 */
public abstract class Elements {

	public abstract void accept(IVisitor visitor);
	public abstract void doSomthing();
}