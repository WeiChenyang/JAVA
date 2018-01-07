package bridge;

import bridge.impl.Jacket;
import bridge.impl.Lady;
import bridge.impl.Man;
import bridge.impl.Trouser;
import bridge.interfaces.Clothing;
import bridge.interfaces.Person;

public class main {

	public static void main(String[] args) {  
		  
        Person man = new Man();  
  
        Person lady = new Lady();  
  
        Clothing jacket = new Jacket();  
  
        Clothing trouser = new Trouser();  
  
        jacket.personDressCLoth(man);
        trouser.personDressCLoth(man);
  
        jacket.personDressCLoth(lady);
        trouser.personDressCLoth(lady);
    }  
}
