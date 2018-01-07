package writeExcel.util;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

public abstract class ExcelExportUtil {
	
	HSSFWorkbook wb = null;
	CellStyle baseStyle,headerStyle, titleStyle, centerStyle;
	private String timePattern = "yyyy-MM-dd";//如果需要可以在实例化对象的时候改变这个值
	private int scale = 2;//小数默认是两位
	
	public ExcelExportUtil(){
		wb = new HSSFWorkbook();
		initStyles();
	}
	
	public ExcelExportUtil(HSSFWorkbook wb){
		this.wb = wb;
		initStyles();
	}
	
	public void initStyles(){
		HSSFFont font = wb.createFont(); 
		HSSFFont fontH = wb.createFont(); 
//		font.setFontName("仿宋_GB2312"); 
		font.setFontHeightInPoints((short) 10);
		fontH.setFontHeightInPoints((short) 20); 
		baseStyle = wb.createCellStyle();
		baseStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		baseStyle.setFont(font);
		
		titleStyle = wb.createCellStyle();
		titleStyle.cloneStyleFrom(baseStyle);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
		titleStyle.setBorderRight(CellStyle.BORDER_THIN);
		titleStyle.setBorderTop(CellStyle.BORDER_THIN);
		titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
		titleStyle.setWrapText(true);// 指定单元格自动换行 
		
		centerStyle = wb.createCellStyle();
		centerStyle.cloneStyleFrom(baseStyle);
		centerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		centerStyle.setBorderRight(CellStyle.BORDER_THIN);
		centerStyle.setBorderTop(CellStyle.BORDER_THIN);
		centerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		centerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		headerStyle= wb.createCellStyle();
		headerStyle.cloneStyleFrom(baseStyle);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerStyle.setFont(fontH);
	}
	
	public abstract void fillExcellData(HSSFWorkbook wb) throws Exception;
	
	/**
	 * 创建HSSFSheet工作簿
	 * @param wb
	 * @param sheetName
	 * @return
	 */
	public HSSFSheet createSheet(HSSFWorkbook wb, String sheetName) {
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);
		return sheet;
	}

	/**
	 * 创建HSSFRow
	 * @param sheet
	 * @param rowNum
	 * @param height
	 * @return
	 */
	public HSSFRow createRow(HSSFSheet sheet, int rowNum, int height) {
		HSSFRow row = sheet.createRow(rowNum);
		row.setHeight((short) height);
		return row;
	}
	/**
	 * 功能：合并单元格
	 * 
	 * @param sheet
	 *            HSSFSheet
	 * @param firstRow
	 *            int
	 * @param lastRow
	 *            int
	 * @param firstColumn
	 *            int
	 * @param lastColumn
	 *            int
	 * @return int 合并区域号码
	 */
	public static int mergeCell(HSSFSheet sheet, int firstRow, int lastRow,
			int firstColumn, int lastColumn) {
		return sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow,
				firstColumn, lastColumn));
	}
	/**
	 * 文字居中的单元格
	 * @param row
	 * @param colNum
	 * @param value
	 */
	public void createHeaderCell(HSSFRow row, int colNum, Object value){
		this.createCell(row, colNum, value, this.headerStyle);
	}
	private void createCell(HSSFRow row, int colNum, Object val, CellStyle style){
		if(val == null){
			HSSFCell cell = row.createCell(colNum);
			//对EXCEL单元格设置单元格格式 
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			cell.setCellValue("");
		}else{
			if(val instanceof Number){
				HSSFCell cell = row.createCell(colNum);
				//对EXCEL单元格设置单元格格式 
				cell.setCellStyle(style);
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC); 
				cell.setCellValue(trimDouble(val.toString()));
			}else if(!(val instanceof Date)){
				HSSFCell cell = row.createCell(colNum);
				//对EXCEL单元格设置单元格格式 
				cell.setCellStyle(style);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
				cell.setCellValue(val.toString());
			}
		}
	}
	/**
	 * 带背景色的单元格
	 * @param row
	 * @param colNum
	 * @param value
	 */
	public void createTitleCell(HSSFRow row, int colNum, String value){
		this.createCell(row, colNum, value, this.titleStyle);
	}
	/**
	 * 设置单元格格式为文本格式
	 */
	public void setCellStyle(){
		this.centerStyle.setDataFormat(wb.createDataFormat().getFormat("@"));
	}
	/**
	 * 文字居中的单元格
	 * @param row
	 * @param colNum
	 * @param value
	 */
	public void createCenterCell(HSSFRow row, int colNum, Object value){
		this.createCell(row, colNum, value, this.centerStyle);
	}
	/**
	 * 限定行，列来创建下拉框
	 * 返回绑定下拉框内容的HSSFDataValidation对象
	 * sheet.addValidationData(dataValidation)
	 * 行范围从 0~Integer.MAX_VALUE
	 * 列范围从0~Integer.MAX_VALUE
	 * @param sheet
	 * @param startRow
	 * @param endRow
	 * @param startCol
	 * @param endCol
	 * @param menus
	 * @return
	 */
	public HSSFDataValidation createDownMenu(int startRow, int endRow, int colNum,String[] menus){
		CellRangeAddressList regions;
		DVConstraint constraint;
		HSSFDataValidation dataValidation;
        // 设置第9列为下拉列表  参数  行范围从 0~Integer.MAX_VALUE   列为第9列，从0开始计数
        regions = new CellRangeAddressList(startRow, endRow, colNum, colNum);
        // 创建下拉列表数据
        constraint = DVConstraint.createExplicitListConstraint(menus);
        // 绑定
        dataValidation = new HSSFDataValidation(regions, constraint);
        return dataValidation;
	}
	/**
	 * 如果带小数，保留小数到指定位
	 * @param str
	 * @return
	 */
	BigDecimal one = new BigDecimal("1");
	private Double trimDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");     
		if(pattern.matcher(str).matches()){
			BigDecimal bd = new BigDecimal(str);
			BigDecimal retVal = bd.divide(one,scale,BigDecimal.ROUND_HALF_UP);
			return retVal.doubleValue();
		}
		return Double.valueOf(str);
	}
	
	/**
	 * 将Excel写出到输出流
	 * @param fileName
	 * @throws IOException
	 */
	public void write2Response(HttpServletResponse response, String fileName) throws IOException{
		if(fileName.length()==0||fileName==null||fileName.trim()==""){
			fileName = System.currentTimeMillis()+".xls";
		}
		try {
			fillExcellData(wb);
			this.write2Response(response, fileName, wb);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	//导出excel
	private void write2Response(HttpServletResponse response, String fileName, HSSFWorkbook wb) throws IOException {
		response.setContentType("application/msexcel");
		response.setContentType("application/octet-stream"); 
		if(fileName.indexOf(".xls") < 0){
			fileName += ".xls";
		}
		response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(fileName,"UTF-8"));
		OutputStream ouputStream = null;
		try {
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != ouputStream){
				ouputStream.flush();    
				ouputStream.close();    
			}
		}  
	}
}