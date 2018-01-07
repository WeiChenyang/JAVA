package interpreter;
/** 
 *   解释器具体实现类。 
 */  
public class MinusExpression extends AbstractExpression {  
  
    @Override  
    public void interpret(Context context) {  
        String in = context.getIn();  
        int v = Integer.parseInt(in);  
        context.setOut(--v);  
    }  
}