package util.utils.interfaces;
/**
 * 批量导入转换类
 */
public interface BatchImpHandler {
	/**
	 * 将每一项转化成 Array Struct
	 * @param object
	 * @return
	 */
	 Object[] getParam(Object object) ;
}