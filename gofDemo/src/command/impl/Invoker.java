package command.impl;

import command.interfaces.Command;

public class Invoker {

	//调用者持有命令对象
	private Command command;

	public Command getCommand() {
		return command;
	}
	public void setCommand(Command command) {
		this.command = command;
	}
	//执行命令
	public void runCommand(){
		command.execute();
	}
	//撤销命令
	public void unDoCommand(){
		command.undo();
	}
}