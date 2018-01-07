package batchUpload.run.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import batchUpload.run.dao.UploadDao;
import batchUpload.run.vo.User;

public class UpService {
    private static UploadDao uploadDao = UploadDao.getInstance();

	public static String upload(List<User> list){
		List<String> sqlList = new ArrayList<String>();
		Iterator<?> it = list.iterator();
		while (it.hasNext()){
			String sqlStr = "insert into upload_user(user_id,user_name,sex,age,birthday) values(";
			User user = (User) it.next();
			sqlStr = sqlStr +"'"+user.getUserId()+"','"+user.getUserName()+"','"+user.getSex()+"',"
					+ "'"+user.getAge()+"','"+user.getBirthday()+"')";
			sqlList.add(sqlStr);
			sqlStr = "";
		}
		return uploadDao.upSql(sqlList);
	}
}