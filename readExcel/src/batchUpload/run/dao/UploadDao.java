package batchUpload.run.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import batchUpload.batchUtils.DBTool;

public class UploadDao {
	
	private UploadDao(){}
	private static final UploadDao uploadDao = new UploadDao();
	public static UploadDao getInstance(){
		return uploadDao;
	}

	public String upSql(List<String> sqlList){
		Connection connection = DBTool.getConnection();
		String str = "";
		try{
			connection.setAutoCommit(false);
			Statement pst =
			connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			for(int i =0;i<sqlList.size();i++){
				System.out.println(sqlList.get(i));
				pst.addBatch(sqlList.get(i));
			}
			pst.executeBatch();
			connection.commit();
			connection.close();
			DBTool.closeConnection(connection);
			str = "³É¹¦";
		}catch(Exception e){
			e.printStackTrace();
			try {
				connection.rollback();
				connection.close();
				DBTool.closeConnection(connection);
				str = "Ê§°Ü";
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return str;
	}
	
	
	/*public void batch(){
		List<String> sqlList = new ArrayList<String>();
		String[] args = new String[sqlList.size()];
		for (int i = 0; i < sqlList.size(); i++) {
			args[i] = (String) sqlList.get(i);
		}
		this.getJdbcTemplate().batchUpdate(args);
	}*/
}