package mediator.impl;

import mediator.interfaces.Department;
import mediator.interfaces.Mediator;

/**
 * @author Administrator
 * 财务部
 */
public class Finacial implements Department {

	//持有中介者对象引用
	private Mediator mediator;
	public Finacial(Mediator mediator) {
		this.mediator = mediator;
		mediator.register("finacial", this);
	}
	@Override
	public void ownWork() {
		System.out.println("财务部负责财务");
	}

	@Override
	public void outWork() {
		System.out.println("请求市场部配合");
		mediator.command("Market");
	}

}
