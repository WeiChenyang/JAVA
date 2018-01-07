package memento;

/**
 * @author Administrator
 * 发起人
 */
public class Originator {

	//需要保存的属性
	private String state;
	
	//创建备忘录，将需要保存的信息导入并实例化出memento对象
	public Memento createMemento(){
		return new Memento(state);
	}
	//恢复备忘录，将memento导入并恢复相关数据
	public void setMemento(Memento memento){
		state = memento.getState();
	}
	//显示数据
	public void show(){
		System.out.println("state = " + state);
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}