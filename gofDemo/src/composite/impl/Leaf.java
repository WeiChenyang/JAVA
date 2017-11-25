package composite.impl;

import composite.abstracts.Component;

/**
 * @author Administrator
 * 表示叶节点对象。叶子节点没有子节点。
 */
public class Leaf extends Component {

	public Leaf(String name) {
		super(name);
	}

	@Override
	public void add(Component component) {
		System.out.println("不能添加一个叶节点");
	}

	@Override
	public void remove(Component component) {
		System.out.println("不能移除一个叶节点");
	}

	@Override
	public void display(int depth) {
		String temp = "";
		for (int i = 0; i < depth; i++) {
			temp += '-';
		}
		System.out.println(temp + name);
	}
}