package template;

import template.abstracts.BenchMark;
import template.extendses.MethodBenchMark;

public class Main {

	public static void main(String[] args) {
		BenchMark benchMark = new MethodBenchMark();
		long returnNum = benchMark.repeat(10);
		System.out.println("returnNum : " + returnNum);
	}
}