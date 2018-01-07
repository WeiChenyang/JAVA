package state.impl;

import state.interfaces.IState;

/**
 * @author Administrator
 * ×´Ì¬A
 */
public class StateA implements IState {  
  
    @Override  
    public void handle(final String sampleParameter) {  
        System.out.println("StateA handle £º" + sampleParameter);  
    }  
}  