package chain.extendses;

import chain.abstracts.Handler;

public class ConcreteHandler extends Handler {

	@Override
	public void doHandler() {
		if (getNextHandler() != null) {
			System.out.println("还有责任链");
			getNextHandler().doHandler();
		} else {
			System.out.println("自己处理");
		}
	}

}
