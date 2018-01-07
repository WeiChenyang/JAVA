package flyWeightFactory.impl;

import java.util.HashMap;
import java.util.Map;

import flyWeightFactory.interfaces.ChessFlyWeight;

/**
 * @author Administrator
 * 享元工厂
 * 创建并管理享元对象，享元池一般设计为键值对。
 */
public class ChessFlyWeightFactory {

	//享元池
	private static Map<String,ChessFlyWeight> map = new HashMap<String,ChessFlyWeight>();
	
	public static ChessFlyWeight getChess(String color){
		if (map.get(color) != null) {
			return map.get(color);
		} else {
			ChessFlyWeight cfw = new ConcreteChess(color);
			map.put(color, cfw);
			return cfw;
		}
	}
}