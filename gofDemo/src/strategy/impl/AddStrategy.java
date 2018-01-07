package strategy.impl;

import strategy.interfaces.Strategy;

/**
 * @author Administrator
 * ¼Ó·¨
 */
public class AddStrategy implements Strategy{

	@Override
	public int calculate(int a, int b) {
		return a+b;
	}
}