package template.extendses;

import template.abstracts.BenchMark;

/**
 * @author Administrator
 * 子类中对方法benchMark（）进行具体实现
 */
public class MethodBenchMark extends BenchMark {

	@Override
	public void benchMark() {
		System.out.println("benchMark()方法的具体实现！");
	}

}
