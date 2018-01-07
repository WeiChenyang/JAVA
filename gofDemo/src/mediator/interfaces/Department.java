package mediator.interfaces;

/**
 * @author Administrator
 * 抽象部门类接口
 */
public interface Department {

	//本部门自己的工作
	public void ownWork();
	//需要其他部门配合的工作
	public void outWork();
}