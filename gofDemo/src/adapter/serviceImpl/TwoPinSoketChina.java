package adapter.serviceImpl;

import adapter.iService.TwoPinSoket;

/**
 * @author Administrator
 * 中国两孔插座      符合手机标准
 */
public class TwoPinSoketChina implements TwoPinSoket {

	@Override
	public void chargeWithTwoPin() {
		System.out.println("中国标准的两孔插座");
	}

	@Override
	public int voltage() {
		return 220;
	}
}