package interpreter;
/** 
 *  解释器上下文环境类。用来存储解释器的上下文环境，比如需要解释的文法等 
 * */  
public class Context {  
    private String in ;  
    private int out ;  
      
    public Context(String in) {  
        this.in = in ;   
    }  
  
    public int getOut() {  
        return out;  
    }  
  
    public void setOut(int out) {  
        this.in = String.valueOf( out ) ;    
        this.out = out;  
    }  
  
    public String getIn() {  
        return in;  
    }  
}