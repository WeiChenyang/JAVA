package adapter.iService;

/**
 * @author Administrator
 * 适配目标     手机充电器两孔插头
 * （无法插入美国三孔插头，电压也不符合标准）  接口
 */
public interface TwoPinSoket {
	public void chargeWithTwoPin();
	public int voltage();
}
