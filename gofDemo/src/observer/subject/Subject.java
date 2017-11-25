package observer.subject;

import java.util.ArrayList;
import java.util.List;

import observer.iObserver.Observer;

/**
 * @author Administrator
 * 目标类
 */
public class Subject {
	//建立观察者set集合
	//private final Set<Observer> observers = new HashSet<Observer>();
	private final List<Observer> observers = new ArrayList<Observer>();
	//目标状态
	private int state;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	//附加，添加观察者
	public void attach(Observer observer){
		observers.add(observer);
	}
	//通告所有观察者
	public void notifyAllObservers(){
		for (Observer observer : observers) {
			observer.update();
		}
	}
}