package flyWeightFactory.impl;

import flyWeightFactory.interfaces.ChessFlyWeight;

/**
 * @author Administrator
 * 具体享元类
 * 为内部状态提供成员变量进行存储
 */
public class ConcreteChess implements ChessFlyWeight{

	private String color;
	
	public ConcreteChess(String color) {
		super();
		this.color = color;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public void display(Coordinate c) {
		System.out.println("棋子颜色：" + color);
		System.out.println("棋子坐标：" + c.getX() + "," + c.getY());
	}
}