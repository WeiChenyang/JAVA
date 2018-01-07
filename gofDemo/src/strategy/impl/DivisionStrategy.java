package strategy.impl;

import strategy.interfaces.Strategy;

/**
 * @author Administrator
 * 除法
 */
public class DivisionStrategy implements Strategy{

	@Override
	public int calculate(int a, int b) {
		if (a!=0) {
			return a/b;
		} else {
			throw new RuntimeException("除数不能为零！");
		}
	}
}