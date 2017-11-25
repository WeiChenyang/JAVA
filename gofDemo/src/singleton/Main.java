package singleton;

import java.util.Scanner;

import singleton.hungryModel.HungrySingleton;
import singleton.lazyModel.LazySingleton;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println("Singleton 请输入数字：");
		int num = input.nextInt();
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>饿汉式<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		HungrySingleton hungrySingletonOne = HungrySingleton.getInstance();
		hungrySingletonOne.setHsId(num);
		System.out.println("饿汉式：hungrySingletonOne.getHsId()>>>>>>>" + hungrySingletonOne.getHsId());
		HungrySingleton hungrySingletonTwo = HungrySingleton.getInstance();
		System.out.println("饿汉式：hungrySingletonTwo.getHsId()>>>>>>>" + hungrySingletonTwo.getHsId());
		judge(hungrySingletonOne,hungrySingletonTwo);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>懒汉式<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		LazySingleton lazySingletonOne = LazySingleton.getInstance();
		lazySingletonOne.setLsId(num);
		System.out.println("懒汉式：lazySingletonOne.getHsId()>>>>>>>" + lazySingletonOne.getLsId());
		LazySingleton lazySingletonTwo = LazySingleton.getInstance();
		System.out.println("懒汉式：lazySingletonTwo.getHsId()>>>>>>>" + lazySingletonTwo.getLsId());
		judge(lazySingletonOne,lazySingletonTwo);
	}
	
	/**
	 * @param arg1
	 * @param arg2
	 * 对象判断    是否引用同一个地址    地址判断
	 */
	public static void judge(Object arg1,Object arg2){
		if (arg1 == arg2) {
			System.out.println(arg1.getClass().getSimpleName() + "  == " + arg2.getClass().getSimpleName());
		} else {
			System.out.println(arg1.getClass().getSimpleName() + "  != " + arg2.getClass().getSimpleName());
		}
	}
}
