package util.utils.vo;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 实现查询结果的简单封装
 */
public class QueryResult {
	//private static Logger loger = Logger.getLogger(QueryResult.class);
	// 记录总条数
	private long recordCount; 
	// 当前结果起始记录号
	private long recordNo; 
	// 当前结果起始记录号
	private long pageSize; 
	// 当前查询页的结果
	@SuppressWarnings("rawtypes")
	private List beanList; 

	/**
	 * 不分页构造函数
	 * @param beanList 未分页的数据列表
	 */
	@SuppressWarnings("rawtypes")
	public QueryResult(List beanList) {
		recordCount = beanList.size();
		recordNo = 0;
		pageSize = recordCount;
		this.beanList = beanList;
	}

	/**
	 * 分页结果构造函数
	 * @param recordCount 记录总数
	 * @param recordNo 起始记录号
	 * @param pageSize 分页大小
	 * @param beanList 分页后的数据列表
	 */
	@SuppressWarnings("rawtypes")
	public QueryResult(long recordCount, long recordNo, long pageSize, List beanList) {
		this.recordCount = recordCount;
		this.recordNo = recordNo;
		this.pageSize = pageSize;
		this.beanList = beanList;
	}
	/**
	 * 无总数分页构造函数
	 * @param recordNo 起始记录号
	 * @param pageSize 分页大小
	 * @param beanList 未分页的数据列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public QueryResult(long recordNo, long pageSize, List beanList) {
		this.recordCount = beanList.size();
		this.recordNo = recordNo;
		this.pageSize = pageSize;
		List tempList = new ArrayList();
		long endNum = recordNo + pageSize;
		if(endNum > beanList.size()){
			endNum = beanList.size();
		}
		for(int i = Integer.parseInt(Long.toString(recordNo)) ; i < endNum ; i++){
			tempList.add(beanList.get(i));
		}
		this.beanList = tempList;
	}

	public long getRecordCount() {
		return this.recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public long getRecordNo() {
		return this.recordNo;
	}
	public void setRecordNo(long recordNo) {
		this.recordNo = recordNo;
	}

	public long getPageSize() {
		return this.pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	@SuppressWarnings("rawtypes")
	public List getObjectList() {
		return this.beanList;
	}
	@SuppressWarnings("rawtypes")
	public void setObjectList(List beanList) {
		this.beanList = beanList;
	}

	//  可用的XML
	public String toXmlString() {
		throw new RuntimeException();
	}
	
	//  可用的JSON字符串
	public String toJSONString() {
		if(beanList instanceof List){
			JSONArray list = new JSONArray();
			for (Object bean : beanList) {
				list.add(JSONObject.fromObject(bean));
			}
			return list.toString();
		}
		return "{}";
	}

	// 输出字符串
	public String toString(String format) {
		throw new RuntimeException();
	}
	
	public String toString() {
		return "QueryResult {recordCount: " + recordCount + ", recordNo: " + recordNo + ", pageSize: " + pageSize + ", objectList: " + ((beanList == null)?0:beanList.size()) + "}";
	}
//	// 输出extjs可用的XML
//	public String toXmlString() {
//		return JSONHelper.getXmlFromList(recordCount, beanList);
//	}
//
//	// 输出extjs可用的JSON字符�?
//	public String toJSONString() {
//		return JSONHelper.getTextFromList(recordCount, beanList);
//	}

	// 输出字符�?
//	public String toString(String format) {
//		if ("XML".equalsIgnoreCase(format)) {
//			return toXmlString();
//		}
//		return toJSONString();
//	}
}