package util.utils.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import util.utils.common.DbBaseClass;
import util.utils.interfaces.IQueryRecordCount;
import util.utils.interfaces.IResultSetHandler;

/**
 * 存储过程调用类
 * @author 
 */
public class ProcCaller extends DbBaseClass {

	private int flag;
	private String msg;
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
     * 构造函数
     * @param dbConn <code>Connection</code> 参数设置数据库调用所使用的连接对象
     * 
     */
	public ProcCaller(Connection dbConn) {
		super(dbConn);
	}
	
    /**
     * 根据设定参数运行存储函数，并将结果以字符串方式返回
     * @param funcName 执行的存储函数名称
	 * @return 函数返回值
	 * @throws SQLException if a database access error occurs
     */
    public Object callFunc(String funcName) 
    	throws SQLException {
    	return this.callFunc(funcName, new Object[]{});
    }
    /**
     * 根据设定参数运行存储函数，并将结果以字符串方式返回
     * @param funcName 执行的存储函数名称
	 * @param params 参数数组
	 * @return 函数返回值
	 * @throws SQLException if a database access error occurs
     */
    public Object callFunc(String funcName, Object[] params) 
    	throws SQLException {
    	Connection conn = dbConn;
    	return this.callFunc(conn, funcName, params);
    }
    /**
     * 根据设定参数运行存储函数，并将结果以字符串方式返回
     * @param conn The connection to use to run the function.
     * @param funcName 执行的存储函数名称
	 * @return 函数返回值
     * @throws SQLException 
	 * @throws SQLException if a database access error occurs
     */
    public Object callFunc(Connection conn, String funcName) 
    	throws SQLException {
    	return this.callFunc(conn, funcName, new Object[]{});
    }
    /**
     * 根据设定参数运行存储函数，并将结果以字符串方式返回
     * @param conn The connection to use to run the function.
     * @param funcName 执行的存储函数名称
	 * @param params 参数数组
	 * @return 函数返回值
	 * @throws SQLException if a database access error occurs
     */
    public Object callFunc(Connection conn, String funcName, Object[] params) 
    	throws SQLException {
    	
        CallableStatement stmt = null;
		Object result = null;
		String str = "";
		if (params != null) {
			for(int idx = 0; idx < params.length; idx ++) {
				if (idx == 0) {
					str = "?";
				} else {
					str = str + ",?";
				}
			}
		}
        try {
            String callStatement = "{? = call " + funcName + "(" + str + ")}";
			//logger.debug("ProcCall callFunc callStatement: " + callStatement);
            stmt = this.prepareCall(conn, callStatement);
            this.fillFunc(stmt, params);
            stmt.execute();
            result = stmt.getObject(1);
        } catch (SQLException e) {
        	//this.rethrow(e, funcName, params);
        	e.printStackTrace();
        }  
		return result;
    }
    /* (non-Javadoc)
     * @see util.common.DbBaseClass#query(java.sql.Connection, java.lang.String, java.lang.Object[], util.interfaces.IResultSetHandler)
     */
    public Object query(Connection conn,String procName,Object[] params,
            IResultSetHandler rsh)
            throws SQLException {
    	
    	if( null == params){
    		params = new Object[0];
    	}

    	//logger.debug("ProcCall query(" + conn + "," + procName + "," + params + "," + rsh + ")");
        String varParams = "";
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				varParams = varParams + "?,";
			}
		}
        String callStatement = "{call " + procName + "(" + varParams + "?,?,?)}";
		boolean needQueryCount = false;
		boolean returnByOutParameter = false;
        if (rsh instanceof IQueryRecordCount) {
			IQueryRecordCount rshCount = (IQueryRecordCount)rsh;
			needQueryCount = rshCount.isNeedQueryCount();
			returnByOutParameter = rshCount.isReturnByOutParameter();
        }
		if (needQueryCount && returnByOutParameter) {
			callStatement = "{call " + procName + "(" + varParams + "?,?,?,?)}";
		}
		//logger.debug("ProcCall query callStatement = " + callStatement);
        CallableStatement stmt = null;
        ResultSet rs = null;
        Object result = null;
        try {
            stmt = this.prepareCall(conn, callStatement);
            this.fillProc(stmt, params, needQueryCount && returnByOutParameter);
            stmt.execute();
			int paramLen = params.length;
			if (needQueryCount && returnByOutParameter) {
				// 传入记录总数传出参数
				((IQueryRecordCount)rsh).setQueryRecordCount(stmt.getLong(paramLen + 1));
				paramLen ++;
			}
            flag = stmt.getInt(paramLen + 1);
            msg = stmt.getString(paramLen + 2);
            //logger.debug("ProcCall call returned (flag=" + flag + ", msg=" + msg + ")");
           if (flag != 101) { 
                rs = (ResultSet)stmt.getObject(paramLen + 3);
            	if(rsh != null){
            		result = rsh.handle(rs);
            	}else{
            		result = getFirstValueFromResultSet(rs);
            	}
            } 
        } catch (SQLException e) {
            //this.rethrow(e, procName, params);
            e.printStackTrace();
        } finally{
        	if(stmt != null){
        		stmt.close();
        	}
        }
        return result;
    }
    
    /**
     * @param rs
     * @return
     */
    private Object getFirstValueFromResultSet(ResultSet rs) {
    	try {
    		if(rs.next()){
    			return rs.getObject(1);
    		}
		} catch (Exception e) {
		}
		return null+"";
	}

	/**
     * Factory method that creates and initializes a 
     * <code>CallableStatement</code> object for the given SQL.  
     * <code>ProcCaller</code> methods always call this method to prepare 
     * statements for them.  Subclasses can override this method to provide 
     * special CallableStatement configuration if needed.  This implementation
     * simply calls <code>conn.prepareCall(sql)</code>.
     *  
     * @param conn The <code>Connection</code> used to create the 
     * <code>CallableStatement</code>
     * @param sql The SQL statement to prepare.
     * @return An initialized <code>CallableStatement</code>.
     * @throws SQLException if a database access error occurs
     */
    private CallableStatement prepareCall(Connection conn, String sql)
        throws SQLException {
            
        return conn.prepareCall(sql);
    }
    
    /**
     * Fill the <code>CallableStatement</code> replacement parameters with
     * the given objects.
     * @param stmt Procedure CallableStatement
     * @param params Query replacement parameters; <code>null</code> is a valid
     *               value to pass in.
     * @throws SQLException if a database access error occurs
     */
    private void fillProc(CallableStatement stmt, Object[] params, boolean appendCountOutParameter)
            throws SQLException {
        if (params == null||params.length==0) {
        	params = new Object[0];
        }
        for (int i = 0; i < params.length; i++) {
			//logger.info("params[" + i + "] = " + ObjectUtils.toString(params[i], "<null>"));
			if(params[i] instanceof java.sql.Array){
				stmt.setArray(i + 1, (java.sql.Array)params[i]);
			}else {
				stmt.setObject(i + 1, params[i]);
			}
		}

		int paramLen = params.length;
		if (appendCountOutParameter) {
			// 传入记录总数传出参数
			//logger.debug("paramLen: " + paramLen);
			// return recordcount
	        stmt.registerOutParameter(paramLen + 1, Types.NUMERIC);  
			paramLen ++;
		}
		// 传入3个固定输出型参数
		// return flag
        stmt.registerOutParameter(paramLen + 1, Types.NUMERIC);  
        // return message
        stmt.registerOutParameter(paramLen + 2, Types.VARCHAR); 
        // return rs
        stmt.registerOutParameter(paramLen + 3, -10);           
    }

    /**
     * Fill the <code>CallableStatement</code> replacement parameters with
     * the given objects.
     * @param stmt Function CallableStatement
     * @param params Query replacement parameters; <code>null</code> is a valid
     *               value to pass in.
     * @throws SQLException if a database access error occurs
     */
    private void fillFunc(CallableStatement stmt, Object[] params)
            throws SQLException {
    	// 传入返回参数
    	// function return value
    	stmt.registerOutParameter(1, Types.VARCHAR);        
        if (params == null||params.length==0) {
            return;
        }
		for (int i = 0; i < params.length; i++) {
			//logger.info("params[" + i + "] = " + ObjectUtils.toString(params[i], "<null>"));
			stmt.setObject(i + 2, params[i]);
		}
    }
    
    /**
     * 执行有参存储过程
     * @param conn 数据库连接
     * @param procName 存储过程名
     * @param params 参数数组
     * @return 结果集
     */
    public ResultSet executeProc(Connection conn, String procName, Object[] params) throws SQLException {
    	//logger.debug("executeProc query(" + conn + "," + procName + "," + params + ")");
        String varParams = "";
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				varParams = varParams + "?,";
			}
		}
        String callStatement = "{ call " + procName + "(" + varParams + "?,?,?)}";
		//logger.debug("executeProc query callStatement = " + callStatement);
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.prepareCall(conn, callStatement);
            this.fillProc(stmt, params,false);
            stmt.executeQuery();
			int paramLen = params.length;
            flag = stmt.getInt(paramLen + 1);
            msg = stmt.getString(paramLen + 2);
            //logger.debug("ProcCall call returned (flag=" + flag + ", msg=" + msg + ")");
            if (flag == 0) {
                rs = (ResultSet)stmt.getObject(paramLen + 3);
            }
        } catch (SQLException e) {
            //this.rethrow(e, procName, params);
            e.printStackTrace();
        }
        return rs;
    }
}