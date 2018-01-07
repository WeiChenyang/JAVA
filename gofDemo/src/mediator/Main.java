package mediator;

import mediator.impl.Development;
import mediator.impl.Finacial;
import mediator.impl.Market;
import mediator.impl.President;
import mediator.interfaces.Mediator;

public class Main {

	public static void main(String[] args) {
		Mediator president=new President();

        Development devp=new Development(president);
        Finacial fin=new Finacial(president);
        Market mar=new Market(president);

        devp.ownWork();
        devp.outWork();
        System.out.println("----------------------------");
        fin.ownWork();
        fin.outWork();
        System.out.println("----------------------------");
        mar.ownWork();
        mar.outWork();
	}
}