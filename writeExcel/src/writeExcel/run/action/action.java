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
	 * ���ص���ģ��
	 * @return ActionForward page
	 */
	public void downExcel(HttpServletRequest req, HttpServletResponse resp) throws	Exception{
		final String fileName = "Excel����";
		
		try {
			new ExcelExportUtil(){
				/**
				 * @param itemKind
				 * @return
				 * ��ѯ�����ֵ�  ����kindֵ
				 */
				public String[] queryItem(String itemKind){
					String[] menus = new String[]{"��","Ů","��ȷ��"};
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
					
					//*****************************EXCEL ���б������˵�����*********************************
			        //������1 
			        sheet = addDownMenuData(sheet,2, Integer.MAX_VALUE, 3, this.queryItem(" "));
			        //������2
			        sheet = addDownMenuData(sheet,2, Integer.MAX_VALUE, 6, this.queryItem(" "));
			        //������3
			        sheet = addDownMenuData(sheet,2, Integer.MAX_VALUE, 8, this.queryItem(" "));
			        
					int colNum = 0, rowNum=0;
					HSSFRow row = this.createRow(sheet, rowNum++, 700);
					//��ͷ��Ϣ
					//������
					ExcelExportUtil.mergeCell(sheet, 0, 0, 0,22);
					this.createHeaderCell(row, 0, "����");
					sheet.setColumnWidth(0, 3000);
					sheet.setColumnWidth(1, 6000);
					sheet.setColumnWidth(2, 5000);
					sheet.setColumnWidth(3, 8000);
					row = this.createRow(sheet, rowNum++, 600);
					colNum = 0;
					//��ͷ
					Map<String, Integer> map = new LinkedHashMap<String, Integer>();
					map.put("�ֶ�1",3000);
					map.put("�ֶ�2",4000);
					map.put("�ֶ�3",4000);
					map.put("�ֶ�4",3000);
					map.put("�ֶ�5",6000);
					map.put("�ֶ�6",5000);
					map.put("�ֶ�7",3000);
					map.put("�ֶ�8",6000);
					map.put("�ֶ�9",3000);
					map.put("�ֶ�10",5000);
					map.put("�ֶ�11",5000);
					map.put("�ֶ�12",5000);
					map.put("�ֶ�13",6000);
					map.put("�ֶ�14",5000);
					map.put("�ֶ�15",3000);
					map.put("�ֶ�16",5000);
					map.put("�ֶ�17",4000);
					map.put("�ֶ�18",4000);
					map.put("�ֶ�19",4000);
					map.put("�ֶ�20",6000);
					map.put("�ֶ�21",4000);
					map.put("�ֶ�22",4000);
					map.put("�ֶ�23",5000);
					for(String key : map.keySet()){
						sheet.setColumnWidth(colNum, Integer.valueOf(map.get(key)));
						this.createTitleCell(row, colNum++, key);
					}
					row = this.createRow(sheet, rowNum++, 500);
					this.setCellStyle();
					this.createCenterCell(row, 0, "ģ�壨ɾ����");
					this.createCenterCell(row, 1, "1111111(����)");
					this.createCenterCell(row, 2, "2222222(����)");
					this.createCenterCell(row, 3, "(��ѡ)");
					this.createCenterCell(row, 4, "3333333(����)");
					this.createCenterCell(row, 5, "(����)");
					this.createCenterCell(row, 6, "(��ѡ)");
					this.createCenterCell(row, 7, "(����)");
					this.createCenterCell(row, 8, "(��ѡ)");
					this.createCenterCell(row, 9, "(����)");
					this.createCenterCell(row, 10, "(����)");
					this.createCenterCell(row, 11, "(����)");
					this.createCenterCell(row, 12, "(����)");
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
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> ��������ʱ��");
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
		//ftlģ����listѭ������word���
		List<Object> docList = new ArrayList<Object>();
		
		docForm.setEmpName(" ");
		docForm.setPerNo(" ");
		docForm.setDateOfDeath(" ");
		docForm.setPaymenYearNum(" ");
		docForm.setBurialType(" ");
		docList.add(docForm);
		
		dataMap.put("list", docList);
		
		String fileName = "����";	//�����ļ�����
		String fileType = "doc";						//�����ļ���չ��
		String templateName = "ftlModel";	//�����ļ�����ģ������
		
		// ���ù������createDoc��������Word�ĵ�  
		// ����      ������Դ���ĵ����ƣ�ģ�����ƣ�response��
		FreeMarkerExportUtil  wg = new FreeMarkerExportUtil(request);
		wg.createDoc(dataMap, fileName, fileType,templateName, response);
	}
}