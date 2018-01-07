package mediator.impl;

import mediator.interfaces.Department;
import mediator.interfaces.Mediator;

/**
 * @author Administrator
 * 市场部
 */
public class Market implements Department {

	//持有中介者对象引用
	private Mediator mediator;
	public Market(Mediator mediator) {
		this.mediator = mediator;
		mediator.register("Market", this);
	}
	@Override
	public void ownWork() {
		System.out.println("市场部负责项目承接工作");
	}

	@Override
	public void outWork() {
		System.out.println("请求研发部配合");
		mediator.command("development");
	}
}