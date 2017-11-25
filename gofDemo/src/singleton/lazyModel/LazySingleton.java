package singleton.lazyModel;

/**
 * @author Administrator
 * 单例模式  懒汉式
 * 只是第一次实例化
 */
public class LazySingleton {
	
	private LazySingleton(){}
	private static LazySingleton lazySingleton = null;
	public static synchronized LazySingleton getInstance(){
		if (lazySingleton == null) {
			lazySingleton = new LazySingleton();
		}
		return lazySingleton;
	}
	
	private int lsId;
	public int getLsId() {
		return lsId;
	}

	public void setLsId(int lsId) {
		this.lsId = lsId;
	}
}
