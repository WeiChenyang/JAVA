package strategy.impl;

import strategy.interfaces.Strategy;

/**
 * @author Administrator
 * ³Ë·¨
 */
public class MultiplyStrategy implements Strategy{

	@Override
	public int calculate(int a, int b) {
		return a*b;
	}
}