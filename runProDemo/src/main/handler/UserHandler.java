package main.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.vo.User;
import util.utils.handlers.PageObjectListHandler;

public class UserHandler extends PageObjectListHandler {
	
	public UserHandler(boolean needQueryCount) {
		super(needQueryCount);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object handle(ResultSet rs) throws SQLException {
	
			List rows = new ArrayList();
			
			while(rs.next()){		
				User user = new User();
				user.setId(rs.getString("ID"));
				user.setUserName(rs.getString("USER_NAME"));
				user.setSex(rs.getString("SEX"));
				user.setAge(rs.getString("AGE"));
				rows.add(user);
			}		
			return rows;
		}
}

