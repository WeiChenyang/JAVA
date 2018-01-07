package memento;

public class Main {
	
    public static void main(String[] args) {  
        //初始状态为on  
        Originator o = new Originator();  
        o.setState("on");  
        o.show();  
          
        //保存状态，隐藏了Originator的实现细节  
        Caretaker c = new Caretaker();  
        c.setMemento(o.createMemento());  
          
        o.setState("off");  
        o.show();  
          
        //恢复状态  
        o.setMemento(c.getMemento());  
        o.show();  
    } 
}