package main.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.service.RunProcedureService;
import main.vo.User;
import util.utils.vo.QueryResult;
import util.utils.vo.ResultFlagMsg;

public class Main {
	private RunProcedureService runProcedureService = null;
	
	private static Main main = new Main();
	@SuppressWarnings("unused")
	private static Main getInstance(){
		return main;
	}
	
	public static void main(String[] args) throws SQLException {
		main.runProcedureQueryDemo();
		main.runFunctionQueryDemo();
		//main.runProcedureBatchDemo();
	}
	
	/**
	 * @throws SQLException
	 * 执行procedure demo
	 */
	@SuppressWarnings("unchecked")
	public void runProcedureQueryDemo() throws SQLException{
		runProcedureService = RunProcedureService.getInstance();
		User user = User.getInstance();
		//User user = new User();
		user.setUserName(" ");
		QueryResult qr = runProcedureService.proQueryDemo(user);
		List<User> list = qr.getObjectList();
		for (User user2 : list) {
			System.out.println(user2.getId() + ","+ user2.getUserName() + "," + user2.getSex() + "," + user2.getAge());
		}
	}
	
	/**
	 * @throws SQLException
	 * 执行function demo
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void runFunctionQueryDemo() throws SQLException{
		runProcedureService = RunProcedureService.getInstance();
		User user = User.getInstance();
		//User user = new User();
		user.setUserName("小1");
		boolean rb = runProcedureService.funQueryDemo(user);
		System.out.println("runFunctionQueryDemo>>>"+user.getUserName()+"》》"+rb);
		
	}
	
	/**
	 * @throws SQLException
	 * 执行procedure demo
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void runProcedureBatchDemo() throws SQLException{
		runProcedureService = RunProcedureService.getInstance();
		User form = User.getInstance();;
		form.setUserName("小红");;
		form.setSex("女");
		form.setAge("18");
		List list = new ArrayList<User>();
		list.add(form);
		boolean resultFlag = false;
		String resultMsg = "";
		try {
			ResultFlagMsg rfm = runProcedureService.proAddUserBatch(form,list);
			if(rfm.getFlag() == 0){
				resultFlag = true;
			}
			resultMsg = rfm.getMsg();
		} catch (Exception e) {
			resultMsg = e.getMessage();
			e.printStackTrace();
		}
		System.out.println("resultFlag>>"+resultFlag+",resultMsg>>"+resultMsg);
	}
}
