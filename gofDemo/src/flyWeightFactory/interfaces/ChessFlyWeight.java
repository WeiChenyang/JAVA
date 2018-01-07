package flyWeightFactory.interfaces;

import flyWeightFactory.impl.Coordinate;

/**
 * @author Administrator
 * 抽象享元类
 * 通常是一个接口或抽象类，声明公共方法，这些方法可以向外界提供对象的内部状态，设置外部状态。
 */
public interface ChessFlyWeight {

	//获取颜色，内部状态
	public String getColor();
	//展示位置，外部状态
	public void display(Coordinate c);
}
