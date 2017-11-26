/*
 * Copyright © 2008 PCCW Solutions All right reserved.
 *
 */
package util.utils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.utils.interfaces.IQueryRecordCount;

/**
 * @author Administrator
 *已经实现 IQueryRecordCount 接口，对应最后返回参数数量为<b>4</b>的存储过程为
 */
public class DynamicListHandler extends DynamicObjectHandler implements  IQueryRecordCount {
	// 是否需要设置查询参数
	boolean needQueryCount = false; 
	// 使用输出参数返回记录数，否则查询记录总数在结果列 COUNT_BY_OVER_
	boolean returnByOutParameter = false; 
	// 记录总数
	long queryRecordCount = 0;

	@SuppressWarnings("rawtypes")
	public DynamicListHandler(Class clazz,boolean needQueryCount) {
		super(clazz,false);
		this.needQueryCount = needQueryCount;
		this.returnByOutParameter = true;
	}
	@SuppressWarnings("rawtypes")
	public DynamicListHandler(Class clazz,boolean needQueryCount, boolean returnByOutParameter) {
		super(clazz,false);
		this.needQueryCount = needQueryCount;
		this.returnByOutParameter = returnByOutParameter;
	}
	public boolean isNeedQueryCount() {
		return needQueryCount;
	}
	public void setNeedQueryCount(boolean needQueryCount) {
		this.needQueryCount = needQueryCount;
	}
	public boolean isReturnByOutParameter() {
		return returnByOutParameter;
	}
	public void setReturnByOutParameter(boolean returnByOutParameter) {
		this.returnByOutParameter = returnByOutParameter;
	}
	public long getQueryRecordCount() {
		return queryRecordCount;
	}
	public void setQueryRecordCount(long queryRecordCount) {
		this.queryRecordCount = queryRecordCount;
	}

	/* (non-Javadoc)
	 * @see util.handler.DynamicObjectHandler#handle(java.sql.ResultSet)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public Object handle(ResultSet rs) throws SQLException {
		boolean initialized = false;
		ArrayList rows = new ArrayList();
		while (rs.next()) {
			if (needQueryCount && !returnByOutParameter && !initialized) {
				this.queryRecordCount = rs.getLong(IQueryRecordCount.COUNT_COLUMN_NAME);
				initialized = true;
			}
			try {
				rows.add(super.handleObject(clazz,rs));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rows;
	}
}