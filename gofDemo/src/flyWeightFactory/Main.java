package flyWeightFactory;

import flyWeightFactory.impl.ChessFlyWeightFactory;
import flyWeightFactory.impl.Coordinate;
import flyWeightFactory.interfaces.ChessFlyWeight;

public class Main {

	public static void main(String[] args) {
		ChessFlyWeight chess1 = ChessFlyWeightFactory.getChess("黑色");
		ChessFlyWeight chess2 = ChessFlyWeightFactory.getChess("黑色");
		System.out.println(chess1);
		System.out.println(chess2);
		
		System.out.println("------增加外部抓过你太的处理----");
		chess1.display(new Coordinate(10,10));
		chess2.display(new Coordinate(20,20));
	}
}