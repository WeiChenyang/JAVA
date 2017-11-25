package singleton.hungryModel;

/**
 * @author Administrator
 * 单例模式  饿汉式
 */
public class HungrySingleton {
	//私有化
	private HungrySingleton() {
	}
	private static final HungrySingleton hungrySingleton = new HungrySingleton();
	//提供访问接口
	public static HungrySingleton getInstance(){
		return hungrySingleton;
	}
	
	private int hsId;
	public int getHsId() {
		return hsId;
	}
	public void setHsId(int hsId) {
		this.hsId = hsId;
	}
}