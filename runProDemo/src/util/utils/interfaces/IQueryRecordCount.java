package util.utils.interfaces;

/**
 * 为ProcCaller添加查询结果大小特性
 * 存储过程需要注册OUT参数number类型, 置于倒数第4个参数位置，即flag参数前面
 * IQueryRecordCount
 */
public interface IQueryRecordCount  extends IResultSetHandler{
	// 记录总数列名
	public static final String COUNT_COLUMN_NAME = "COUNT_BY_OVER_"; 
	// 查询的记录总数
	/**
	 * 是否需要设置查询参数
	 */
	boolean isNeedQueryCount();

	/**
	 * 是否需要设置查询参数
	 */
	void setNeedQueryCount(boolean needQueryCount);

	/**
	 * 是否使用输出参数返回记录数，否则查询记录总数在结果列 COUNT_BY_OVER_
	 */
	boolean isReturnByOutParameter();

	/**
	 * 设置是否使用输出参数返回记录数，否则查询记录总数在结果列 COUNT_BY_OVER_
	 */
	void setReturnByOutParameter(boolean returnByOutParameter);

	/**
	 * 获取查询记录总数
	 */
	long getQueryRecordCount();

	/**
	 * 由ProcCaller设置查询记录总数
	 * @param queryRecordCount 查尊记录总数
	 */
	void setQueryRecordCount(long queryRecordCount);
}
