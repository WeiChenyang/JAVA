package writeExcel.run.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import writeExcel.util.ExcelExportUtil;
import writeExcel.util.FreeMarkerExportUtil;

@SuppressWarnings("serial")
public class action extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String arg = req.getParameter("method");
			if ("downDoc".equalsIgnoreCase(arg)) {
				downDoc(req,resp);
			} else {
				downExcel(req,resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}

	/**
	 * 下载导入模板
	 * @return ActionForward page
	 */
	public void downExcel(HttpServletRequest req, HttpServletResponse resp) throws	Exception{
		final String fileName = "Excel导出";
		
		try {
			new ExcelExportUtil(){
				/**
				 * @param itemKind
				 * @return
				 * 查询数据字典  根据kind值
				 */
				public String[] queryItem(String itemKind){
					String[] menus = new String[]{"男","女","不确定"};
					return menus;
				}
				public HSSFSheet addDownMenuData(HSSFSheet sheet,int startRow, int endRow, int colNum,String[] menus){
					HSSFDataValidation dataValidation = this.createDownMenu(startRow, endRow, colNum, menus);
			        sheet.addValidationData(dataValidation);
					return sheet;
				}
				@Override
				public void fillExcellData(HSSFWorkbook wb) throws Exception {
					HSSFSheet sheet = this.createSheet(wb, fileName);
					
					//*****************************EXCEL 中列表下拉菜单设置*********************************
			        //下拉框1 
			        sheet = addDownMenuData(sheet,2, Integer.MAX_VALUE, 3, this.queryItem(" "));
			        //下拉框2
			        sheet = addDownMenuData(sheet,2, Integer.MAX_VALUE, 6, this.queryItem(" "));
			        //下拉框3
			        sheet = addDownMenuData(sheet,2, Integer.MAX_VALUE, 8, this.queryItem(" "));
			        
					int colNum = 0, rowNum=0;
					HSSFRow row = this.createRow(sheet, rowNum++, 700);
					//表头信息
					//设置行
					ExcelExportUtil.mergeCell(sheet, 0, 0, 0,22);
					this.createHeaderCell(row, 0, "标题");
					sheet.setColumnWidth(0, 3000);
					sheet.setColumnWidth(1, 6000);
					sheet.setColumnWidth(2, 5000);
					sheet.setColumnWidth(3, 8000);
					row = this.createRow(sheet, rowNum++, 600);
					colNum = 0;
					//表头
					Map<String, Integer> map = new LinkedHashMap<String, Integer>();
					map.put("字段1",3000);
					map.put("字段2",4000);
					map.put("字段3",4000);
					map.put("字段4",3000);
					map.put("字段5",6000);
					map.put("字段6",5000);
					map.put("字段7",3000);
					map.put("字段8",6000);
					map.put("字段9",3000);
					map.put("字段10",5000);
					map.put("字段11",5000);
					map.put("字段12",5000);
					map.put("字段13",6000);
					map.put("字段14",5000);
					map.put("字段15",3000);
					map.put("字段16",5000);
					map.put("字段17",4000);
					map.put("字段18",4000);
					map.put("字段19",4000);
					map.put("字段20",6000);
					map.put("字段21",4000);
					map.put("字段22",4000);
					map.put("字段23",5000);
					for(String key : map.keySet()){
						sheet.setColumnWidth(colNum, Integer.valueOf(map.get(key)));
						this.createTitleCell(row, colNum++, key);
					}
					row = this.createRow(sheet, rowNum++, 500);
					this.setCellStyle();
					this.createCenterCell(row, 0, "模板（删除）");
					this.createCenterCell(row, 1, "1111111(必填)");
					this.createCenterCell(row, 2, "2222222(必填)");
					this.createCenterCell(row, 3, "(必选)");
					this.createCenterCell(row, 4, "3333333(必填)");
					this.createCenterCell(row, 5, "(必填)");
					this.createCenterCell(row, 6, "(必选)");
					this.createCenterCell(row, 7, "(必填)");
					this.createCenterCell(row, 8, "(必选)");
					this.createCenterCell(row, 9, "(必填)");
					this.createCenterCell(row, 10, "(必填)");
					this.createCenterCell(row, 11, "(必填)");
					this.createCenterCell(row, 12, "(必填)");
					this.createCenterCell(row, 13, "");
					this.createCenterCell(row, 14, "");
					this.createCenterCell(row, 15, "");
					this.createCenterCell(row, 16, "");
					this.createCenterCell(row, 17, "");
					this.createCenterCell(row, 18, "");
					this.createCenterCell(row, 19, "");
					this.createCenterCell(row, 20, "");
					this.createCenterCell(row, 21, "");
					this.createCenterCell(row, 22, "");
				}
			}.write2Response(resp, fileName);
		} catch (Exception e) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> 服务请求超时！");
		}
	}
	
	/**
	 * @param mapping
	 * @param baseForm
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void downDoc(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map<String,Object> dataMap = new HashMap<String,Object>(); 
		VO docForm = new VO();
		//ftl模板中list循环生成word表格
		List<Object> docList = new ArrayList<Object>();
		
		docForm.setEmpName(" ");
		docForm.setPerNo(" ");
		docForm.setDateOfDeath(" ");
		docForm.setPaymenYearNum(" ");
		docForm.setBurialType(" ");
		docList.add(docForm);
		
		dataMap.put("list", docList);
		
		String fileName = "标题";	//导出文件名称
		String fileType = "doc";						//导出文件扩展名
		String templateName = "ftlModel";	//导出文件所需模板名称
		
		// 调用工具类的createDoc方法生成Word文档  
		// 参数      （数据源，文档名称，模板名称，response）
		FreeMarkerExportUtil  wg = new FreeMarkerExportUtil(request);
		wg.createDoc(dataMap, fileName, fileType,templateName, response);
	}
}