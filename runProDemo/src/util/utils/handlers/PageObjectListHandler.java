package util.utils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 * 实现数据库查询分页结果到实体的映射
 */ 
public abstract class PageObjectListHandler extends DynamicListHandler {

	public PageObjectListHandler(boolean needQueryCount) {
		super(null, needQueryCount);
	}
	public PageObjectListHandler(boolean needQueryCount, boolean returnByOutParameter) {
		super(null, needQueryCount, returnByOutParameter);
	}
	public abstract Object handle(ResultSet rs) throws SQLException ;
}