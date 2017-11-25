package observer.observerImpl;

import observer.iObserver.Observer;
import observer.subject.Subject;

/**
 * @author Administrator
 * 观察者1   继承观察者父类
 */
public class ObserverOne extends Observer{

	public ObserverOne(Subject subject) {
		//调用父类中构造函数，将新加观察者添加到目标类总的观察者集合中
		super(subject);
	}

	@Override
	public void update() {
		//重新父类，应用父类中的成员变量
		System.out.println("ObserverOne's state: " + subject.getState());
	}
}