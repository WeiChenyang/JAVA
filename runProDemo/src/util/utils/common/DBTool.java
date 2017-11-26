package util.utils.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Administrator
 * JDBC连接数据库工具
 */
public class DBTool {
	//JDBC链接数据库
	//private static String driverName = "com.mysql.jdbc.Driver";
	//private static String url = "jdbc:mysql://127.0.0.1:3306/my_db";
	private static String driverName = ReadProperties.getDB_DRIVER_NAME();
	private static String url = ReadProperties.getDB_URL();
	private static String user = ReadProperties.getDB_USER_NAME();
	private static String password = ReadProperties.getDB_PASS_WORD();
	private static Connection conn = null;
	
	//单例模式
	private DBTool(){}
	private static DBTool dbTool = new DBTool();
	public static DBTool getDBTool(){
		return dbTool;
	}
	public Connection getConnection(){
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
