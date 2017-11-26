package main.service;

import java.sql.SQLException;
import java.util.List;
import main.handler.UserHandler;
import main.vo.User;
import util.main.run.RunProUtil;
import util.utils.common.FormatUtil;
import util.utils.interfaces.BatchImpHandler;
import util.utils.vo.QueryResult;
import util.utils.vo.ResultFlagMsg;

public class RunProcedureService {
	private RunProUtil rpu = null;
	private RunProcedureService(){}
	private static RunProcedureService runProcedureService = new RunProcedureService();
	public static RunProcedureService getInstance(){
		return runProcedureService;
	}
	
	/**
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public QueryResult proQueryDemo(User user) throws SQLException {
		rpu = RunProUtil.getInstance();
		String procName="PRO_TEST.PRO_QUERY_LIST";	
		Object[] params = new Object[]{
				user.getUserName(),
				new Long(0),
				new Long(Integer.MAX_VALUE)
		};
		UserHandler rsh = new UserHandler(true);
		ResultFlagMsg rfm = rpu.runProc(procName, params, rsh);
		List list = (List)rfm.getResult();
		QueryResult queryResult = new QueryResult(rsh.getQueryRecordCount(), 0,0,list);
		return queryResult;
	}
	
	/**
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean funQueryDemo(User user) throws SQLException {
		rpu = RunProUtil.getInstance();
		String procName = "PRO_TEST.FUN_QUERY_LIST";
		Object[] params = new Object[] { 
				user.getUserName()
		};
		String result = (String) rpu.callFunction(procName, params);
		if("1".equals(result)){
			return true;
		}
		return false;
	}
	
	//保存新增或修改的人员信息
	@SuppressWarnings("rawtypes")
	public ResultFlagMsg proAddUserBatch(User user,List list)throws Exception{
		rpu = RunProUtil.getInstance();
		String procName = "PRO_TEST.PRO_ADD_USER_BATCH";
		String sdescriptor = "USER_NEW_ADD_OBJ".toUpperCase();
		String adescriptor = "USER_NEW_ADD_TAB".toUpperCase();
		Object[] params = new Object[]{
				user.getId(),
				FormatUtil.ARRAY_FLAT
		};
		BatchImpHandler handler = new BatchImpHandler() {
			@SuppressWarnings("unused")
			@Override
			public Object[] getParam(Object object) {
				User info=(User) object;
				return new Object[]{
						user.getId(),
						user.getUserName(),
						user.getAge(),
						user.getSex()
				};
			}
		};
		ResultFlagMsg rfm =rpu.saveImportDataBat(procName, sdescriptor, adescriptor,params, list, handler, rpu,null);
		return rfm;
	}
}