package visitor.commVisitor;

/**
 * @author Administrator
 * B类相对于A类来说，A属于被访问者
 */
public class A {

	public void method1(){
		System.out.println("我是A！");
	}
	public void method2(B b){
		b.method(this);
	}
}
