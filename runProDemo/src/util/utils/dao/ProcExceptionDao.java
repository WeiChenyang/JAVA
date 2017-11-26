package util.utils.dao;

import java.sql.SQLException;
import util.utils.exception.DbException;

/**
 * @author Administrator
 * 存储过程执行调用类    异常信息方法封装
 */
public class ProcExceptionDao {
	/**
	 * 输出参数日志
	 * @param methodName 方法名称
	 * @param params 方法参数组
	 */
	/*public static  void outputParam(Logger  log,String methodName, Object[] params) {
		if (log.isDebugEnabled()) {
			for (int i = 0; i < params.length; i++) {
				 log.debug("|->" + methodName + "[" + i + "] = " + ObjectUtils.toString(params[i], "<null>"));
			}
		}
	}*/
	/**
	 * 抛出DbHelper异常信息
	 * @param dbFlag
	 *            存储过程返回值标识
	 * @param dbMessage
	 *            存储过程返回信息
	 */
	public static void throwDataAccessException(int dbFlag, String dbMessage)
			throws SQLException {
		throwDataAccessException(dbFlag, dbMessage, null);
	}

	/**
	 * 抛出DbHelper异常信息
	 * @param exp
	 *            原始异常信息
	 */
	public static void throwDataAccessException(Exception exp)
			throws SQLException {
		throwDataAccessException(-1, "", exp);
	}

	/**
	 * 抛出DbHelper异常信息
	 * @param dbFlag
	 *            存储过程返回值标识
	 * @param dbMessage
	 *            存储过程返回信息
	 * @param exp
	 *            原始异常信息
	 */
	public static void throwDataAccessException(int dbFlag, String dbMessage,
			Exception exp) throws SQLException {
		if ((dbMessage.length()==0||dbMessage == null) && exp != null) {
			dbMessage = exp.getMessage();
		}
		DbException result = new DbException(dbFlag, dbMessage, exp);
		throw result;
	}
	
	
	/**
     * Throws a new exception with a more informative error message.
     * @param cause The original exception that will be chained to the new 
     * exception when it's rethrown. 
     * @param sql The query that was executing when the exception happened.
     * @param params The query replacement parameters; <code>null</code> is a 
     * valid value to pass in.
     * @throws SQLException if a database access error occurs
     */
     /*public static void rethrow(Logger  logger,SQLException cause, String sql, Object[] params)
        throws SQLException {
		logger.error("@->sql: " + sql);
		if (params == null) {
            logger.error("@->params: []");
        } else {
			logger.error("@->params: " + Arrays.asList(params));
        }
		logger.error(cause, cause);
        throw cause;
    }*/
}
