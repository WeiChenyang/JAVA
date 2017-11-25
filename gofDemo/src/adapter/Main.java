package adapter;

import adapter.serviceImpl.AmericaAdapter;
import adapter.serviceImpl.ChinaPhone;
import adapter.serviceImpl.ThreePinSoketAmerica;
import adapter.serviceImpl.TwoPinSoketChina;

public class Main {

	public static void main(String[] args) {
		// 在中国，用两孔插座充电
        TwoPinSoketChina twoPinSoketChina = new TwoPinSoketChina();
        ChinaPhone chinaPhone = new ChinaPhone();
        chinaPhone.setTwoPinSoket(twoPinSoketChina);
        chinaPhone.chargeRequest();

        // 然后坐飞机去美国旅游，美国某旅馆的墙上有只有一个三孔插座
        ThreePinSoketAmerica threePinSoketAmerica = new ThreePinSoketAmerica();
        threePinSoketAmerica.chargeWithThreePin();
        System.out.println("电压是" + threePinSoketAmerica.voltage() + "伏特\n");

        // 幸好我有美国适配器，一头插到三孔插座，另一头转换成二孔插座，就可以给我的荣耀手机充电
        AmericaAdapter americaAdapter = new AmericaAdapter(threePinSoketAmerica);
        americaAdapter.chargeWithTwoPin();
        System.out.println("电压是" + americaAdapter.voltage() + "伏特\n");

        // 在美国，通过美国适配器，用三空插座充电
        chinaPhone.setTwoPinSoket(americaAdapter);
        chinaPhone.chargeRequest();
	}
}
