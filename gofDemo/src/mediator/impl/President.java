package mediator.impl;

import java.util.HashMap;
import java.util.Map;

import mediator.interfaces.Department;
import mediator.interfaces.Mediator;

/**
 * @author Administrator
 * 总经理      负责协调各部门工作关系
 */
public class President implements Mediator {

	private Map<String,Department> map = new HashMap<String,Department>();
	@Override
	public void register(String name, Department obj) {
		map.put(name, obj);
	}

	@Override
	public void command(String name) {
		map.get(name).ownWork();
	}

}
