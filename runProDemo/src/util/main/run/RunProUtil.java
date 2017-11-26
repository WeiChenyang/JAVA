package util.main.run;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import util.utils.common.DBTool;
import util.utils.common.FormatUtil;
import util.utils.dao.ProcCaller;
import util.utils.dao.ProcExceptionDao;
import util.utils.exception.DbException;
import util.utils.handlers.MsgHandler;
import util.utils.interfaces.BatchImpHandler;
import util.utils.interfaces.IResultSetHandler;
import util.utils.vo.ResultFlagMsg;

/**
 * @author Administrator
 * 运行存储过程 、函数
 */
public class RunProUtil {
	//private static final Logger log = Logger.getLogger(RunProUtil.class);
	//单例模式
	private RunProUtil(){}
	//私有
	private static RunProUtil runProUtil = new RunProUtil();
	//外部调用接口
	public static RunProUtil getInstance(){
		return runProUtil;
	}
	
	public Connection getConnection() throws SQLException {
		return DBTool.getDBTool().getConnection();
	}
	/**
	 * 调用指定名称的存储过程，该存储过程只返回一个值，指明结果是否success
	 * @param procName 存储过程名
	 * @param params 参数列表
	 * @param rsh 结果处理对象
	 * @param isResultFlagMsg 结果实体类
	 * @return 查询结果
	 */
	public ResultFlagMsg runProc(String procName, Object[] params,IResultSetHandler rsh) throws DbException {
		Connection connection = null;
		ResultFlagMsg result = null;
		try {
			connection =getConnection();
			result = runProc(connection, procName, params, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(connection != null)
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		return result;
	}
	/**
	 * 根据设定参数运行存储函数，并将结果以字符串方式返回
	 * @param funcName 执行的存储函数名称
	 * @param params 参数数组
	 * @return 函数返回值
	 */
	public Object callFunction(String funcName, Object[] params)
			throws DbException {
		Connection connection =  null;
		try {
			connection = getConnection();
			//log.info(">>>callFunction(funcName:" + funcName + ", params)");
			//ProcExceptionDao.outputParam(log, "callFunction", params);
			Object result = null;
			try {
				ProcCaller run = new ProcCaller(connection);
				result = run.callFunc(funcName, params);
			} catch (SQLException exp) {
				//log.error(">error: callFunction(funcName: " + funcName + ")",exp);
				ProcExceptionDao.throwDataAccessException(-1, "", exp);
			}
			//log.info("<<<callFunction(funcName:" + funcName + ", params): " + result);
			return result;
		} catch (Exception e) {
			throw new DbException(101,e);
		} finally{
			try {
				if(connection != null)
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 由于连接由外部传入，需要调用方需要自己管理连接
	 * 调用指定名称的存储过程，该存储过程只返回一个值，指明结果是否success
	 * @param connection 数据库连接
	 * @param procName 存储过程名
	 * @param params 参数列表
	 * @param rsh 结果处理对象
	 * @return 查询结果
	 */
	public ResultFlagMsg runProc(Connection connection, String procName, Object[] params,
			IResultSetHandler rsh) throws DbException {
		try {
			//log.info(">>>runProcFlagMsg(procName: " + procName + ", params)");
			//ProcExceptionDao.outputParam(log, "runProcFlagMsg", params);

			ResultFlagMsg result = null;
			int dbFlag = -1;
			String dbMessage = "";
			try {
				ProcCaller run = new ProcCaller(connection );
				Object object = run.query(procName, params, rsh);
				dbMessage = run.getMsg();
				dbMessage = MsgHandler.replaceHTML(dbMessage);

				result = new ResultFlagMsg(object, run.getFlag(), dbMessage);
				dbFlag = run.getFlag();
			} catch (SQLException exp) {
				//log.error(">error: runProcFlagMsg(procName: " + procName + ")",exp);
				ProcExceptionDao.throwDataAccessException(dbFlag, dbMessage, exp);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 业务逻辑错误则抛出异常
			if (dbFlag == 101) {
				ProcExceptionDao.throwDataAccessException(dbFlag, dbMessage);
			}
			return result;
		} catch(SQLException dbexp){
			throw new DbException(101, dbexp.getMessage(), dbexp);
		} finally{
			try {
				if(connection != null)
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 嵌套表批量导入工具
	 * 一次全部提交
	 * @param procName 存储过程名
	 * @param Sdescriptor  STRUCT的描述 ,必须大写
	 * @param Adescriptor  ARRAY的描述 ,必须大写
	 * @param params 基本参数，其中嵌套表类型参数 用BatchImpHelper#ARRAY_FLAT 做占位符
	 * @param list 要导入的批量数据集合
	 * @param handler @see BatchImpHandler
	 * @param payDAO
	 * @param ResultSetHandler
	 * @return ResultFlagMsg
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ResultFlagMsg saveImportDataBat(String procName, String Sdescriptor,String Adescriptor,
			Object[] params, List list,BatchImpHandler handler, RunProUtil payDAO,IResultSetHandler rsh) throws Exception {
		
		Sdescriptor = Sdescriptor.toUpperCase();
		Adescriptor = Adescriptor.toUpperCase();
		ARRAY array = null;
		try {
			array = createArray(payDAO, list,
					Sdescriptor,  Adescriptor,handler);
		} catch (Exception e) {
			Exception ex=  new Exception("数据转换出错，请检查数据", e);
			ex.printStackTrace();
			throw ex;
		}
		params = replaceArray(params, array);
		ResultFlagMsg rls = payDAO.runProc(procName, params, rsh);
		return rls;
	}
	/**
	 * 将特定标志位的参数替换成Array
	 * 
	 * @param params
	 * @param array
	 * @return
	 */
	private Object[] replaceArray(Object[] params, ARRAY array) {
		int len = params.length;
		Object []o = new Object[len];
		for(int i=0;i<len;i++){
			o[i]=params[i];
		}
		for (int i = 0; i < o.length; i++) {
			Object object = o[i];
			if (FormatUtil.ARRAY_FLAT.equals(object)) {
				o[i] = array;
			}
		}
		return o;
	}
	@SuppressWarnings({ "rawtypes","unchecked" })
	private ARRAY createArray(RunProUtil payDAO, List listTab, String Sdescriptor,String Adescriptor,BatchImpHandler handler)
			throws Exception {
		// String procName =
		// "{ call PAY_AR_TRX_PUB.import_lines(?,?,?,?,?,?,?)}";
		OracleConnection orclConn = (OracleConnection) payDAO.getConnection();
		// OracleConnection连接
		StructDescriptor sd = null;// STRUCT的描述
		ArrayDescriptor ad = null;// ARRAY的描述
		// call = orclConn.prepareCall(procName);
		sd = StructDescriptor.createDescriptor(Sdescriptor, orclConn);// STRUCT的描述，TYPE_FEEDBACK_IMPORT_REC是oracle中的定义的type
		ad = ArrayDescriptor.createDescriptor(Adescriptor, orclConn);//ARRAY的描述，TYPE_FEEDBACK_IMPORT_TAB是oracle中定义的type，嵌套表
		List listS = new ArrayList();
		//log.debug("***********Create Oracle Array*****************");
		//log.debug("StructDescriptor:" + Sdescriptor);
		//log.debug("ArrayDescriptor:" + Adescriptor);
		for (Object listData : listTab) {
			//log.debug(ArrayUtils.toString(listData));
			Object[] paras =handler. getParam(listData);
			listS.add(new STRUCT(sd, orclConn, paras));
			//log.debug(ArrayUtils.toString(paras));
		}
		//log.debug("**************************************************");
		STRUCT[] structArr = (STRUCT[]) listS.toArray(new STRUCT[0]);
		return new ARRAY(ad, orclConn, structArr);
	}
}
