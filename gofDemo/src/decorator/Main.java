package decorator;

import decorator.impl.Employe;
import decorator.impl.ManagerA;
import decorator.impl.ManagerB;
import decorator.interfaces.Project;

/**
 * @author Administrator
 * 例子：项目经理接到一个项目，项目最终要完成编码。
 *	项目经理接到项目后，先做些前期的工作（比如需求分析、设计），
 *	然后将编码工作委派给代码工人，代码工人干完后，项目经理做项目的收尾工作。
 */
public class Main {

	public static void main(String[] args) {
		Project employe = new Employe();
		Project managerA = new ManagerA(employe);
		Project managerB = new ManagerB(employe);
		
		//项目经理做事情
		managerA.writeCodes();
		managerB.writeCodes();
	}
}