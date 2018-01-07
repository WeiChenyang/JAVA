package state.impl;

import state.interfaces.IState;

public class Context {  
  
    /** 
     * 持有一个State类型的对象实例 
     */  
    private IState state;  
  
    public void setState(final IState state) {  
        this.state = state;  
    }  
  
    /** 
     * 用户感兴趣的接口方法 
     */  
    public void request(final String sampleParameter) {  
        // 转调state来处理  
        state.handle(sampleParameter);  
    }  
}