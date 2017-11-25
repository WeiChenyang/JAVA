package visitor.commVisitor;

public class Main {

	public static void main(String[] args) {
		A a = new A();
		a.method1();
		//b∑√Œ a
		B b = new B();
		b.method(a);
	}
}
