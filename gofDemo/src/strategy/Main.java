package strategy;

import strategy.impl.AddStrategy;
import strategy.impl.Context;
import strategy.impl.DivisionStrategy;
import strategy.impl.MultiplyStrategy;
import strategy.impl.SubstractStrategy;

public class Main {
	 public static void main(String[] args) {
	
	        //加法
	        Context context=new Context(new AddStrategy());
	        System.out.println(context.calculate(10, 5));
	        //减法
	        Context context2=new Context(new SubstractStrategy());
	        System.out.println(context2.calculate(3, 2));
	        //乘法
	        Context context3=new Context(new MultiplyStrategy());
	        System.out.println(context3.calculate(6, 8));
	        //除法
	        Context context4=new Context(new DivisionStrategy());
	        System.out.println(context4.calculate(90, 9));    
	    }
}

