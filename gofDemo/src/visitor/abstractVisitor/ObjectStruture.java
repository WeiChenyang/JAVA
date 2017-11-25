package visitor.abstractVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import visitor.abstractVisitor.impl.ConcreteElement1;
import visitor.abstractVisitor.impl.ConcreteElement2;
import visitor.abstractVisitor.interfaces.Elements;

/**
 * @author Administrator
 * 结构对象
 */
public class ObjectStruture {
	public static List<Elements> getList(){
		List<Elements> list = new ArrayList<Elements>();
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			int a = rand.nextInt();
			if (a>50) {
				list.add(new ConcreteElement1());
			} else {
				list.add(new ConcreteElement2());
			}
		}
		return list;
	}
}