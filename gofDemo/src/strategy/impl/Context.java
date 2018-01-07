package strategy.impl;

import strategy.interfaces.Strategy;

public class Context {

	private Strategy strategy;
	
	public Context(Strategy strategy){
		this.strategy = strategy;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	public int calculate(int a,int b){
		return strategy.calculate(a, b);
	}
}