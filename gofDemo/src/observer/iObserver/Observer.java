package observer.iObserver;

import observer.subject.Subject;

/**
 * @author Administrator
 * 观察者父类
 */
public abstract class Observer {

	//引用目标类
	protected Subject subject;
	//观察者初始化，把新加观察者添加到目标类中观察者集合中
	protected Observer(Subject subject){
		this.subject = subject;
		subject.attach(this);
	}
	//父类中声明，观察者子类中必须有update（）
	public abstract void update();
}