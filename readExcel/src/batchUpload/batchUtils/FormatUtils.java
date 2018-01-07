package batchUpload.batchUtils;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Utils.
 * @author 80072051
 */
public class FormatUtils {


	/**
	 * 灏唈ava.util.Date绫诲瀷鐨勫�杞崲涓簓yyy-MM-dd鏍煎紡鐨勫瓧绗︿�?
	 * @param date
	 * @return
	 */
	public static String getFormattedDate(Date date){
		if(date!=null){
			SimpleDateFormat df = new SimpleDateFormat(SystemProperties.DATE_FORMAT_YMD);
			return df.format(date);
		}
		return null;
	}

	/**
	 * 灏唈ava.util.Date绫诲瀷鐨勫�杞崲涓烘寚瀹氭牸寮忕殑瀛楃涓�?
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getFormattedDate(Date date, String pattern){
		if(date!=null && StringUtils.isNotBlank(pattern)){
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	/**
	 * 灏哠tring绫诲瀷鐨勬棩鏈熻浆鍖栦负java.sql.Date瀵硅�?
	 * �?寔鐨勬牸寮忥細"y-M-d", "M/d/y", "yyyy-MM-dd", "MM/dd/yyyy"
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
	 * 灏哠tring绫诲瀷鐨勬棩鏈熻浆鍖栦负java.sql.Date瀵硅�?
	 * 
	 * @param str
	 * @param pattern 闇�璋冪敤鑰呮寚�?�氬瓧绗︿覆鐨勬棩鏈熸牸寮�
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
	 * Get formatted messages.
	 * Replace "\r" and "\n" with "<br>"
	 * @param msg
	 * @return
	 */
	public static String getFormattedMsg(String msg){
		if(StringUtils.isNotBlank(msg)){
			//return StringUtils.trim(StringUtils.replaceEach(msg, new String[]{"\n", "\r", "'"}, new String[]{"<br>", "<br>", "\'"}));
			msg = msg.replaceAll("\\\\n", " \n ");
			msg = msg.replaceAll("\\\\r", " \r ");
//			msg = msg.replaceAll("'", "\'");
			return msg;
		}
		return msg;
	}
	
	/**
	 * Get formatted messages.
	 * Replace "\r" , "\n" , "'" , """ with "<br>"
	 * @param msg
	 * @return
	 */
	public static String getFormattedAlertMsg(String msg){
		if(StringUtils.isNotBlank(msg)){
			msg = msg.replaceAll("\\n\\r", "<br>");
			msg = msg.replaceAll("\\r\\n", "<br>");
			msg = msg.replaceAll("\\n", "<br>");
			msg = msg.replaceAll("\\r", "<br>");
			msg = msg.replaceAll("'", "\'");
			msg = msg.replaceAll("\"", "\\\"");
			return msg;
		}
		return msg;
	}
	
	
	/**
	 * 灏哠tring绫诲瀷鐨勬棩鏈熻浆鍖栦负java.sql.Date瀵硅�? �?寔鐨勬牸寮忥細"y-M-d", "M/d/y", "yyyy-MM-dd",
	 * "MM/dd/yyyy"
	 * 
	 * @param str
	 * @param needUnBlank 闈炵┖鏄惁杩斿洖褰撳墠鏃ユ�?
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
	
	/**
	 * 杩斿洖鏍煎紡鍖栧悗鐨凞ouble
	 * @param d
	 * @return
	 */
	public static String getFormatedDouble(Double d){
		DecimalFormat df = new DecimalFormat( "##0.00## "); 
		if(d!=null){
			return df.format(d);
		}
		return null;
	}
	
	public static String getUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append(" % " + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	
	public static String subString (String str, int beginIndex, int endIndex) {
		if (StringUtils.isBlank(str)) return "";
		
		if (beginIndex > str.length()) return "";
		
		if (endIndex > str.length() ) return str.substring(beginIndex);
		
		return str.substring(beginIndex, endIndex);
	}
	
	/**
	 * 鑾峰彇鏂囦欢鍚庣紑鍚�?
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName){
		int lastIdx = StringUtils.lastIndexOf(fileName, ".");
		if(lastIdx > 0){
			return StringUtils.substring(fileName, lastIdx);
		}
		return "";
	}
	
	/**
	 * 鏍规嵁鏂囦欢绫诲瀷鍔爃ttp header
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getRespHeader(String ext, String charset){
		charset = StringUtils.isNotBlank(charset) ? charset : "UTF-8";
		ext = StringUtils.lowerCase(ext);
		Map map = new HashMap();
		map.put(".xls", "application/ms-excel;charset=" + charset);
		map.put(".xlsx", "application/ms-excel;charset=" + charset);
		map.put(".pdf", "application/pdf;charset=" + charset);
		map.put(".doc", "application/msword;charset=" + charset);
		map.put(".docx", "application/msword;charset=" + charset);
		map.put(".txt", "text/plain;charset=" + charset);
		map.put(".jpg", "image/jpeg");
		map.put(".jpeg", "image/jpeg");
		map.put(".mdb", "application/msaccess");
		map.put(".ppt", "application/vnd.ms-powerpoint");
		map.put(".gif", "image/gif");
		map.put(".png", "image/png");
		
		if(map.containsKey(ext)){
			return (String) map.get(ext);
		}
		return "text/plain;charset=" + charset;
	}
	/**
	 * 鑾峰彇褰撳墠骞存�?
	 * @return
	 */
	public static String getCurrentYearMonth(){
		Calendar calendar = Calendar.getInstance();
		/**
	     * 鑾峰彇骞翠唤
	     */
	    String year = String.valueOf(calendar.get(Calendar.YEAR));
	    /**
	     * 鑾峰彇鏈堜唤
	     */
	    String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
	    if(month.length()==1){
	    	month = "0"+month;
	    }
	    return year+month;
	}
	
}
