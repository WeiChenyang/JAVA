package observer;

import observer.observerImpl.ObserverOne;
import observer.observerImpl.ObserverThree;
import observer.observerImpl.ObserverTwo;
import observer.subject.Subject;

/**
 * @author Administrator
 */
public class Main {
	public static void main(String[] args) {
        Subject subject = new Subject();

        new ObserverOne(subject);
        new ObserverTwo(subject);
        new ObserverThree(subject);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>First subject state change£º<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        subject.setState(15);
        subject.notifyAllObservers();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>First subject state change£º<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Second subject state change£º<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        subject.setState(10);
        subject.notifyAllObservers();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Second subject state change£º<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
