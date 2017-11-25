package prototype.iservice;

/**
 * @author Administrator
 * 抽象原型角色接口
 */
public interface Prototype {
	
	public Prototype clone();
	public String getName();
	public void setName(String name);
}
