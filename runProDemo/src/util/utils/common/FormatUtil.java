package util.utils.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

public class FormatUtil {

	/**
	 * 嵌套表占位符
	 */
	public static final String ARRAY_FLAT = "ARRAY_FLAT";
	
	/**
	 * 将String类型的日期转化为java.sql.Date对象
	 * 支持的格式："y-M-d", "M/d/y", "yyyy-MM-dd", "MM/dd/yyyy"
	 * @param str
	 * @return
	 */
	public static java.sql.Date getSqlDateObj(String str){
		Date date = null;
		if(StringUtils.isNotBlank(str)){
			//String dateValue = str.substring(0, 10);
			String dateValue = StringUtils.substring(str, 0, 10);
			String[] patterns = new String[]{"y-M-d", "M/d/y", "yyyy-MM-dd", "MM/dd/yyyy"};
			try {
				date = DateUtils.parseDate(StringUtils.trim(dateValue), patterns);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date!=null ? new java.sql.Date(date.getTime()) : null; 		
	}
	
	/**
	 * 将String类型的日期转化为java.sql.Date对象
	 * 
	 * @param str
	 * @param pattern 需要调用者指定字符串的日期格式
	 * @return
	 */
	public static java.sql.Date getSqlDateObj(String str, String pattern){
		Date date = null;
		if(StringUtils.isNotBlank(str)){
			//String dateValue = str.substring(0, 10);
			String dateValue = StringUtils.substring(str, 0, 10);
			String[] patterns = new String[]{pattern};
			try {
				date = DateUtils.parseDate(StringUtils.trim(dateValue), patterns);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date!=null ? new java.sql.Date(date.getTime()) : null; 		
	}
	
	public static String toFormat(String date){
		String[] subDateStr = date.split("-");
		StringBuffer sb=new StringBuffer();
		sb.append(subDateStr[2]).append("-").append(subDateStr[1]).append("-").append(subDateStr[0]);
		return sb.toString();
	}
	
	public static Long getLong(long l){
		if(l!=-1){
			return new Long(l);
		}else{
			return null;
		}
	}

	public static Long getLong(String str) {
		Long result = null;
		if (StringUtils.isNotBlank(str)) {
			try {
				result = NumberUtils.createLong(str);
			} catch (Exception e) {
			}
		}
		return result;
	}

	public static BigDecimal getBigDecimalFromString(String s){
		if(null!=s&&!"".equals(s)){
			return new BigDecimal(s);
		}
		return null;
	}
	
	public static String getStringFromBigDecimal(BigDecimal bd){
		if(null!=bd){
			bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			return bd.toString();
			
		}
		return "";
	}
	
	public static Double getDoubleFromBigDecimal(BigDecimal bd){
		if(null!=bd){
			bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			return new Double(bd.toString());
			
		}
		return null;
	}
	
	public static Double getDoubleFromString(String str){
		BigDecimal bd = getBigDecimalFromString(str);
		if(null!=bd){
			bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			return new Double(bd.toString());
			
		}
		return null;
	}
	
	/**
	 * 将String类型的日期转化为java.sql.Date对象 支持的格式："y-M-d", "M/d/y", "yyyy-MM-dd",
	 * "MM/dd/yyyy"
	 * 
	 * @param str
	 * @param needUnBlank 非空是否返回当前日期
	 * 
	 * @return
	 */
	public static java.sql.Date getSqlDateObj(String str, boolean needUnBlank) {
		java.sql.Date date = getSqlDateObj(str);
		if (needUnBlank) {
			return date != null ? date
					: new java.sql.Date(new Date().getTime());
		} else {
			return date;
		}
	}
}
