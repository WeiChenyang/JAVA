package command;

import command.impl.CreateCommand;
import command.impl.Invoker;
import command.impl.Receiver;
import command.interfaces.Command;

public class Main {

	public static void main(String[] args) {
		//创建接收者
		Receiver receiver = new Receiver();
		//创建命令对象，并设置他的接收者
		Command command = new CreateCommand(receiver);
		
		//创建调用者，将命令对象设置进去
		Invoker invoker = new Invoker();
		invoker.setCommand(command);
		
		invoker.runCommand();
		invoker.unDoCommand();
	}
}