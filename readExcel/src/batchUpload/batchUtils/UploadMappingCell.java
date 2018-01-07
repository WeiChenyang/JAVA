package batchUpload.batchUtils;

import java.io.Serializable;

public class UploadMappingCell implements Serializable {

	private static final long serialVersionUID = -3197499466751166636L;

	/**
	 * Excel ��Header����
	 */
	private String header;
	
	/**
	 * ӳ��Java�������
	 */
	private String attribute;
	
	/**
	 * Excel��Ԫ�������У���1��ʼ����
	 */
	private Integer colIndex;
	
	private Integer colWidth;
	
	private boolean nullAbled = false;
	
	/**
	 * ��Ԫ���ʽ
	 */
	private String cellFormat;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Integer getColIndex() {
		return colIndex;
	}

	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	public Integer getColWidth() {
		return colWidth;
	}

	public void setColWidth(Integer colWidth) {
		this.colWidth = colWidth;
	}

	public String getCellFormat() {
		return cellFormat;
	}

	public void setCellFormat(String cellFormat) {
		this.cellFormat = cellFormat;
	}

	public boolean isNullAbled() {
		return nullAbled;
	}

	public void setNullAbled(boolean nullAbled) {
		this.nullAbled = nullAbled;
	}

	
}
