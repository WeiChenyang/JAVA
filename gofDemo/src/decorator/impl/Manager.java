package decorator.impl;

import decorator.interfaces.Project;

/**
 * @author Administrator
 * 项目经理
 */
public class Manager implements Project{

	//调用项目经理管理的程序员
	private Project project;
	public Manager(Project project) {
		this.project = project;
	}

	//项目经理工作
	@Override
	public void writeCodes() {
		startNewWork();
	}

	//项目经理开始自己新的项目
	public void startNewWork(){
		//项目的开发过程
		doEarlyWork();
		project.writeCodes();
		doEndWork();
	}
	
	//项目经理自己在项目开始做的事情
	public void doEarlyWork(){
	}
	
	//项目经理做收尾工作
	public void doEndWork(){
	}
}
