package util.utils.handlers;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import util.utils.interfaces.IResultSetHandler;

/**
 * @author Administrator
 * 该Handler对应最后返回参数数量为<b3</b>的存储过程为
 */
public class DynamicObjectHandler implements IResultSetHandler {
	// 记录总数列名
	public static final String COUNT_COLUMN_NAME = "COUNT_BY_OVER_";

	@SuppressWarnings("rawtypes")
	protected Class clazz;
	protected boolean atom;

	private int columnIndex = 1;
	private String columnName;

	@SuppressWarnings("rawtypes")
	public DynamicObjectHandler(Class clazz) {
		this(clazz, false);
	}
	@SuppressWarnings("rawtypes")
	public DynamicObjectHandler(Class clazz, boolean atom) {
		super();
		this.clazz = clazz;
		this.atom = atom;
	}
	public Object handle(ResultSet rs) throws SQLException {
		if (!atom) {
			return handleList(rs);
		} else {
			return handleAtom(rs);
		}

	}
	public Object handleAtom(ResultSet rs) throws SQLException {
		if (rs.next()) {
			if (columnName == null)
				return rs.getString(columnIndex);
			else
				return rs.getString(columnName);
		} else {
			return null;
		}
	}
	@SuppressWarnings({ "rawtypes", "unused"})
	protected Object handleObject(Class clazz, ResultSet rs) throws Exception {
		Object result = clazz.newInstance();

		Map<String, FiledValue> filedMap = getFiledMap(clazz);

		ResultSetMetaData md = rs.getMetaData();
		int cols = md.getColumnCount();
		for (int i = 1; i <= cols; i++) {
			String colName = md.getColumnName(i);
			FiledValue fdValue = filedMap.get(transferName(colName));
			if (fdValue != null) {
				Field field = fdValue.getField();
				field.setAccessible(true);
				Class fieldType = fdValue.getFieldType();
				String fieldName = fdValue.getFieldName();
				try {
					if (fieldType == String.class) {
						field.set(result, rs.getString(colName));

					} else if (fieldType == Integer.class
							|| fieldType == int.class) {
						field.set(result, rs.getInt(colName));

					} else if (fieldType == Long.class
							|| fieldType == long.class) {
						field.set(result, rs.getLong(colName));

					} else if (fieldType == java.util.Date.class) {
						field.set(result, rs.getDate(colName));

					} else if (fieldType == java.sql.Timestamp.class) {
						field.set(result, rs.getTimestamp(colName));

					} else if (fieldType == Double.class
							|| fieldType == double.class
							|| fieldType == BigDecimal.class) {
						field.set(result, rs.getDouble(colName));
					} else {
						field.set(result, rs.getObject(colName));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//Logger.info("自动映射没有匹配到："+colName+",请确认类属性内容和数据返回结果列对应");
				System.out.println("自动映射没有匹配到："+colName+",请确认类属性内容和数据返回结果列对应");
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	private Object transferName(String colName) {
		if(colName.contains("_")){
			return colName.replaceAll("_", "").toLowerCase();
		}
		if(colName != null){
			return colName.toLowerCase();
		}
		return colName;
	}

	@SuppressWarnings("rawtypes")
	protected Map<String, FiledValue> getFiledMap(Class clazz) {
		Map<String, FiledValue> result = new LinkedHashMap<String, FiledValue>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Class fieldType = field.getType();
			String fieldName = field.getName();
			result.put(transferName(fieldName).toString(), new FiledValue(field, fieldType, fieldName));
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List handleList(ResultSet rs) throws SQLException {

		ArrayList result = new ArrayList();

		while (rs.next()) {
			try {
				result.add(handleObject(clazz, rs));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	protected static class FiledValue {
		Field field;
		@SuppressWarnings("rawtypes")
		Class fieldType;
		String fieldName;

		@SuppressWarnings("rawtypes")
		public FiledValue(Field field, Class fieldType, String fieldName) {
			super();
			this.field = field;
			this.fieldType = fieldType;
			this.fieldName = fieldName;
		}

		public Field getField() {
			return field;
		}
		public void setField(Field field) {
			this.field = field;
		}
		@SuppressWarnings("rawtypes")
		public Class getFieldType() {
			return fieldType;
		}
		@SuppressWarnings("rawtypes")
		public void setFieldType(Class fieldType) {
			this.fieldType = fieldType;
		}
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
	}
}