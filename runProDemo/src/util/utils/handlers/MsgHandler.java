package util.utils.handlers;

/**
 * @author Administrator
 * msg信息转换
 */
public class MsgHandler {
	public static String replaceHTML(String msg){
		if(null == msg){
			return null;
		}
		msg = msg.replaceAll("<br>", "\\\\n");
		msg = msg.replaceAll("<br >", "\\\\n");
		msg = msg.replaceAll("<br />", "\\\\n");
		msg = msg.replaceAll("</?[^>]+>", "");
		return msg;
	}
}