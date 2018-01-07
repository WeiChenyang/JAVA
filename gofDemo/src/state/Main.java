package state;

import state.impl.Context;
import state.impl.StateA;
import state.impl.StateB;
import state.interfaces.IState;

public class Main {

	public static void main(final String[] args) {  
		  
        Context context = new Context();  
  
        IState stateA = new StateA();  
        context.setState(stateA);  
        context.request("test");  
        IState stateB = new StateB();  
        context.setState(stateB);  
        context.request("test");  
    } 
}