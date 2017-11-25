package visitor.commVisitor;

/**
 * @author Administrator
 * B类相对于A类来说，B属于访问者
 */
public class B {

	public void method(A a){
		a.method1();
	}
}
