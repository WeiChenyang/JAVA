package util.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Administrator
 * 读取配置文件
 */
public class ReadProperties {

	//private static final String CFG_MAPPING_FILE = "../config/system.properties";
	private static String DB_DRIVER_NAME;
	private static String DB_URL;
	private static String DB_USER_NAME;
	private static String DB_PASS_WORD;

	static{
		String path = Thread.currentThread().getContextClassLoader().getResource("system.properties").getPath(); 
		Properties prop = null;
		try {
			//InputStream in = new FileInputStream(new File(path+File.separator+CFG_MAPPING_FILE));
			InputStream in = new FileInputStream(new File(path));
			prop = new Properties();
			prop.load(in) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DB_DRIVER_NAME = prop.getProperty("DataBase.driverName");
		DB_URL = prop.getProperty("DataBase.url");
		DB_USER_NAME = prop.getProperty("DataBase.userName");
		DB_PASS_WORD = prop.getProperty("DataBase.passWord");
	}

	public static String getDB_DRIVER_NAME() {
		return DB_DRIVER_NAME;
	}
	public static void setDB_DRIVER_NAME(String dB_DRIVER_NAME) {
		DB_DRIVER_NAME = dB_DRIVER_NAME;
	}
	public static String getDB_URL() {
		return DB_URL;
	}
	public static void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}
	public static String getDB_USER_NAME() {
		return DB_USER_NAME;
	}
	public static void setDB_USER_NAME(String dB_USER_NAME) {
		DB_USER_NAME = dB_USER_NAME;
	}
	public static String getDB_PASS_WORD() {
		return DB_PASS_WORD;
	}
	public static void setDB_PASS_WORD(String dB_PASS_WORD) {
		DB_PASS_WORD = dB_PASS_WORD;
	}
}