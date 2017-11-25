package adapter.serviceImpl;

import adapter.iService.ThreePinSoket;

/**
 * @author Administrator
 * 被适配者， 三孔插座实现类
 */
public class ThreePinSoketAmerica implements ThreePinSoket {

	@Override
	public void chargeWithThreePin() {
		System.out.println("被适配   ：：美国三孔插座");
	}

	@Override
	public int voltage() {
		return 110;
	}

}
