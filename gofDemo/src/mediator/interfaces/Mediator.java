package mediator.interfaces;

/**
 * @author Administrator
 * 抽象中介者
 */
public interface Mediator {

	//记录所有同事类
	public void register(String name,Department obj);
	//发出命令
	public void command(String name);
}