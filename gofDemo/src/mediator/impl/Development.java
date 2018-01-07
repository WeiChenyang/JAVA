package mediator.impl;

import mediator.interfaces.Department;
import mediator.interfaces.Mediator;

/**
 * @author Administrator
 * 研发部
 */
public class Development implements Department {

	//持有中介者对象引用
	private Mediator mediator;
	
	public Development(Mediator mediator) {
		this.mediator = mediator;
		mediator.register("development", this);
	}

	@Override
	public void ownWork() {
		System.out.println("研发部负责研发工作");
	}

	@Override
	public void outWork() {
		System.out.println("研发部请求财务部配合");
		mediator.command("finacial");
	}
}