package adapter.serviceImpl;

import adapter.iService.TwoPinSoket;

/**
 * @author Administrator
 * 中国手机      两孔插头   220V电压
 */
public class ChinaPhone {
	private TwoPinSoket twoPinSoket;

	public ChinaPhone() { }
	
	//手机通过两孔插头获取到的电压
	public void setTwoPinSoket(TwoPinSoket twoPinSoket){
		this.twoPinSoket = twoPinSoket;
	}
	
	public void chargeRequest(){
		System.out.println("中国手机 从两孔插头上获取到的电压为：：" + twoPinSoket.voltage() + "V!");
	}
}