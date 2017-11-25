package composite;

import composite.impl.Composite;
import composite.impl.Leaf;

/**
 * @author Administrator
 * 通过 Component 接口操作结构中的对象。
 */
public class Main {

	public static void main(String[] args) {
		Composite root = new Composite("root");
        root.add(new Leaf("Leaf A"));
        root.add(new Leaf("Leaf B"));
        
        Composite compX = new Composite("Composite X");
        compX.add(new Leaf("Leaf XA"));
        compX.add(new Leaf("Leaf XB"));
        root.add(compX);
        
        Composite compXY = new Composite("Composite XY");
        compXY.add(new Leaf("Leaf XYA"));
        compXY.add(new Leaf("Leaf XYB"));
        compX.add(compXY);
        
        root.display(1);
	}
}