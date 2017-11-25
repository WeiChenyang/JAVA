package observer.observerImpl;

import observer.iObserver.Observer;
import observer.subject.Subject;

/**
 * @author Administrator
 * π€≤Ï’ﬂ2
 */
public class ObserverTwo extends Observer{

	public ObserverTwo(Subject subject) {
		super(subject);
	}
	@Override
	public void update() {
		System.out.println("ObserverTwo's state: " + subject.getState());
	}
}