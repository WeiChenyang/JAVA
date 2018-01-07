package batchUpload.batchUtils;

import java.io.File;
import java.util.Properties;

public class SystemProperties {

	private static Properties _p = null;
	private static Properties _db_p = null;
	private static String uploadLogPath = null;
	
	//日期默认格式yyyy-MM-dd
	public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
	//用于上传的对象中必须包含的属性
	public static final String ATTR_EXCEL_ROW_INDEX = "excelRowIndex";
	
	static{
		uploadLogPath = new File("WebRoot").getAbsolutePath() + "/WEB-INF/uploadLog";
	}
	public String getUploadLogPath() {
		return uploadLogPath;
	}
	public static String getPropertieForDB(String key) {
		if (_db_p == null) {
			return null;
		}
		return _db_p.getProperty(key);
	}
	public static String get(String key) {
		return get(key, null);
	}
	public static String get(String key, String defaultVal) {
		String val = getPropertieForDB(key);
		if (val == null) {
			val = _p.getProperty(key);
		}
		if ((val == null) || ("".equals(val))) {
			return defaultVal;
		}
		return val;
	}
}