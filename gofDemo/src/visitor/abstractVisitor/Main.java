package visitor.abstractVisitor;

import java.util.List;

import visitor.abstractVisitor.impl.Visitor;
import visitor.abstractVisitor.interfaces.Elements;

public class Main {

	public static void main(String[] args) {
		List<Elements> list = ObjectStruture.getList();
		for (Elements elements : list) {
			elements.accept(new Visitor());
		}
	}
}