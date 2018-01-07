package state.impl;

import state.interfaces.IState;

public class StateB implements IState {  
  
    @Override  
    public void handle(final String sampleParameter) {  
        System.out.println("StateB handle £º" + sampleParameter);  
    }  
} 