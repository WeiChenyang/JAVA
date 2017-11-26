package util.utils.interfaces;

import java.sql.SQLException;

/**
 * @author Administrator
 * 处理公共父类，将ResultSet转化成对应返回结果
 */
public interface IResultSetHandler {
 
	public abstract  Object handle(java.sql.ResultSet rs)
			throws  SQLException;
}