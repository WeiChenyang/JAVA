package interpreter;

public class Main {
	
	public static void main(String[] args) {  
        Context context = new Context( "20" ) ;  
          
        new MinusExpression().interpret(context) ;   
        new PlusExpression().interpret(context) ;   
        new MinusExpression().interpret(context) ;   
        new PlusExpression().interpret(context) ;     
        System.out.println( context.getOut() );   
    } 
}