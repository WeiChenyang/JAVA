package util.utils.exception;

/**
 * 数据库异常信息封装
 */
@SuppressWarnings("serial")
//RuntimeException DataAccessException
public class DbException extends  RuntimeException {
	// 存储过程返回值标识
	private int dbFlag;
  
	public DbException() {
		super("");
	}
	public DbException(String message) {
		super(message);
	}
	public DbException(String message, Throwable cause) {
		super(message, cause);
	}
	public DbException(Throwable cause) {
		super("",cause);
	}
	/**
	 * 构造函数
	 * @param dbFlag 存储过程返回值标识
	 * @param dbMessage 存储过程返回信息
	 */
	public DbException(int dbFlag, String dbMessage) {
		super(dbMessage);
		this.dbFlag = dbFlag;
	}
	/**
	 * 构造函数
	 * @param dbFlag 存储过程返回值标识
	 * @param dbMessage 存储过程返回信息
	 * @param exp 原始异常信息
	 */
	public DbException(int dbFlag, String dbMessage, Exception exp) {
		super(dbMessage,exp);
		this.dbFlag = dbFlag; 
	}
	public DbException(int dbFlag, Throwable cause) {
		super("",cause);
		this.dbFlag = dbFlag; 
	}
	public int getDbFlag() {
		return this.dbFlag;
	}
	public void setDbFlag(int dbFlag) {
		this.dbFlag = dbFlag;
	}
}