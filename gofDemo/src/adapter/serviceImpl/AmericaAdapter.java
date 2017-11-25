package adapter.serviceImpl;

import adapter.iService.ThreePinSoket;
import adapter.iService.TwoPinSoket;

/**
 * @author Administrator
 *　适配器   去适配美国的三孔插座
 *
 *  实现两孔插座标准
 *  在类中组合三孔插座
 *  适配器  转换 
 *  将两孔插头通过适配器可使用三孔插座
 */
public class AmericaAdapter implements TwoPinSoket {

	private ThreePinSoket threePinSoket;
	
	public AmericaAdapter(ThreePinSoket threePinSoket){
		this.threePinSoket = threePinSoket;
	}
	
	//两孔插头插入适配器，获取三孔插座
	@Override
	public void chargeWithTwoPin() {
		threePinSoket.chargeWithThreePin();
	}

	//两孔插头插入适配器，获取三孔插座电压
	//将美国110V电压转换为中国两孔220V标准电压返回
	@Override
	public int voltage() {
		return threePinSoket.voltage()*2;
	}
}