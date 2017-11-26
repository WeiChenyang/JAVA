package util.utils.vo;

/**
 * 返回有result, flag, msg结果实体类
 */
public class ResultFlagMsg {
	private Object result;
	private int flag;
	private String msg;
	public ResultFlagMsg() {
	}

	public ResultFlagMsg(Object result, int flag, String msg) {
		this.result = result;
		this.flag = flag;
		this.msg = msg;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("ResultFlagMsg{");
		str.append(result);
		str.append(",");
		str.append(flag);
		str.append(",");
		str.append(msg);
		str.append("}");
		return str.toString();
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
