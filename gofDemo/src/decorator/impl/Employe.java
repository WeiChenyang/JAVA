package decorator.impl;

import decorator.interfaces.Project;

/**
 * @author Administrator
 * 码农
 */
public class Employe implements Project {

	//写代码
	@Override
	public void writeCodes() {
		System.out.println("码农在码代码，写啊写，加班的写，终于写完啦");
	}
}