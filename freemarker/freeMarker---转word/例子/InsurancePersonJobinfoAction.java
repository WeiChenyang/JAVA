/*
 *File Name: YOUR FILE NAME
 *Copyright pccw.com. 2003-2011, All rights reserved.
 *Created by: YOUR  NAME
 *Created Date:  2013-04-22 10:14:34
 */
package com.pccw.business.heccInsurance.endowmentInsurance.action;

import java.io.File;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pccw.business.heccInsurance.endowmentInsurance.form.InsurancePersonJobinfoForm;
import com.pccw.business.heccInsurance.endowmentInsurance.form.InsurancePersonTempForm;
import com.pccw.business.heccInsurance.endowmentInsurance.model.InsurancePersonJobinfoOfferInfo;
import com.pccw.business.heccInsurance.endowmentInsurance.service.InsurancePersonJobinfoService;
import com.pccw.business.heccInsurance.endowmentInsurance.vo.InsurancePersonJobinfoVO;
import com.pccw.business.heccInsurance.endowmentInsurance.vo.InsurancePersonOutWordVO;
import com.pccw.business.heccInsurance.endowmentInsurance.vo.InsurancePersonRdeathOutWordVO;
import com.pccw.business.heccInsurance.endowmentInsurance.vo.InsurancePersonTempVOImpl;
import com.pccw.business.heccPayChangePush.action.SentOAPushListInfo;
import com.pccw.business.pub.PAYAction;
import com.pccw.business.pub.vo.QueryResult;
import com.pccw.business.pub.vo.ResultFlagMsg;
import com.pccw.business.util.ExcelExportUtil;
import com.pccw.business.util.ExcelUtil;
import com.pccw.business.util.FMExcelExportUtil;
import com.pccw.business.util.PayUtils;
import com.pccw.business.util.SysParam;
import com.pccw.business.util.FreeMarkerExportUtil;
import com.pccw.business.util.exc.BusinessImportExternalDataToDB;
import com.pccw.business.workflowinfo.service.WorkflowInfoService;
import com.pccw.business.workflowinfo.vo.WorkflowInfoVOImpl;
import com.pccw.kernel.common.COMMON;
import com.pccw.kernel.common.SpringBeanFactory;
import com.pccw.kernel.common.User;
import com.pccw.kernel.user.vo.ArchUserVOImpl;

/**
 * 控制层Action类，控制页面跳转，调用Service层处理业务逻辑
 * 
 * @author
 */
public class InsurancePersonJobinfoAction extends PAYAction {
	private static InsurancePersonJobinfoService service = (InsurancePersonJobinfoService) SpringBeanFactory
			.getBean("insurancePersonJobinfoService");
	private static WorkflowInfoService wservice = (WorkflowInfoService) SpringBeanFactory
			.getBean("workflowInfoService");

	// private static SentPushListInfoService pushService
	// =(SentPushListInfoService)
	// SpringBeanFactory.getBean("sentPushListInfoService");
	// private static PayChangeworkService pcService =
	// (PayChangeworkService)SpringBeanFactory.getBean("payChangeService");

	/**
	 * @param mapping
	 * @param baseForm
	 * @param request
	 * @param response
	 * @throws Exception
	 * 在职员工养老保险信息变更   导出申报表 word
	 */
	@SuppressWarnings("static-access")
	public ActionForward exportToWord(ActionMapping mapping, ActionForm baseForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取参数信息
		String ids = request.getParameter("value");
		String period = request.getParameter("dataPeriod");
		String[] arr = ids.split("-");
		String bussinessId = arr[1];
		String winsuredType = arr[2];
		
		User user = (User) request.getSession().getAttribute("User");
		String userId = user.getUserId();
		
		ResultFlagMsg rfm = service.getExportToWordData(userId,period,bussinessId,winsuredType);
		List list = (List) rfm.getResult();
		
		/*System.out.println("+----------------list test------------------+");
		for (int i = 0; i < list.size(); i++) {
			InsurancePersonOutWordVO v = (InsurancePersonOutWordVO) list.get(i);
			System.out.print(v.getACCOUNT_NATURE() + "  ,");
		}
		System.out.println();
		System.out.println("+-------------------------------------------+");*/
		/*String msg = "";
		if (list.size()==0) {
			msg = "人员权限不足！";
		} else if (rfm.getFlag()==0) {
			
		} else{
			msg = rfm.getMsg();
		}*/
		InsurancePersonOutWordVO v = (InsurancePersonOutWordVO) list.get(0);
		Map<String,Object> dataMap=new HashMap<String,Object>(); 
		//所有key值必须和ftl模板中${}中字段值对应一样
		dataMap.put("orgNo",COMMON.isEmpty(v.getOrg_Code_New())?"":v.getOrg_Code_New());
		dataMap.put("empName",COMMON.isEmpty(v.getEmp_name_new())?"":v.getEmp_name_new());
		dataMap.put("personNo", COMMON.isEmpty(v.getPER_NO())?"":v.getPER_NO());
		dataMap.put("firstEmpName", COMMON.isEmpty(v.getEmp_name())?"":v.getEmp_name());
		dataMap.put("lastEmpName", COMMON.isEmpty(v.getEmp_name_new())?"":v.getEmp_name_new());
		dataMap.put("firstIdCard", COMMON.isEmpty(v.getIdentity_id())?"":v.getIdentity_id());
		dataMap.put("lastIdCard", COMMON.isEmpty(v.getIdentity_id_new())?"":v.getIdentity_id_new());
		dataMap.put("firstBir", COMMON.isEmpty(v.getDATE_OF_BIRTH())?"":v.getDATE_OF_BIRTH());
		dataMap.put("lastBir", COMMON.isEmpty(v.getDATE_OF_BIRTH_new())?"":v.getDATE_OF_BIRTH_new());
		dataMap.put("firstPerson", COMMON.isEmpty(v.getIDENTITY_P())?"":v.getIDENTITY_P());
		dataMap.put("lastPerson", COMMON.isEmpty(v.getIDENTITY_P_new())?"":v.getIDENTITY_P_new());
		dataMap.put("firstSex", COMMON.isEmpty(v.getSex())?"":v.getSex());
		dataMap.put("lastSex", COMMON.isEmpty(v.getSex_new())?"":v.getSex_new());
		dataMap.put("firstRegister", COMMON.isEmpty(v.getACCOUNT_NATURE())?"":v.getACCOUNT_NATURE());
		dataMap.put("lastRegister", COMMON.isEmpty(v.getACCOUNT_NATURE_new())?"":v.getACCOUNT_NATURE_new());
		dataMap.put("firstEmpSource", COMMON.isEmpty(v.getSTAFF_SOURCE())?"":v.getSTAFF_SOURCE());
		dataMap.put("lastEmpSource", COMMON.isEmpty(v.getSTAFF_SOURCE_new())?"":v.getSTAFF_SOURCE_new());
		dataMap.put("firstNation", COMMON.isEmpty(v.getNATION())?"":v.getNATION());
		dataMap.put("lastNation", COMMON.isEmpty(v.getNATION_new())?"":v.getNATION_new());
		dataMap.put("firstWorkTime", COMMON.isEmpty(v.getJOIN_WORK_DATE())?"":v.getJOIN_WORK_DATE());
		dataMap.put("lastWorkTime",COMMON.isEmpty(v.getJOIN_WORK_DATE_new())?"":v.getJOIN_WORK_DATE_new());
		dataMap.put("firstEmpNumber", COMMON.isEmpty(v.getPERSONAL_NUMBER())?"":v.getPERSONAL_NUMBER());
		dataMap.put("lastEmpNumber", COMMON.isEmpty(v.getPERSONAL_NUMBER_new())?"":v.getPERSONAL_NUMBER_new());
		
		String fileName = winsuredType;					//文件名称
		String fileType = "doc";						//文件扩展名
		String templateName = "insurancePersonJobinfo";	//模板名称
		
		// 调用工具类的createDoc方法生成Word文档  
		// 参数      （数据源，文档名称，模板名称，response）
		FreeMarkerExportUtil  wg = new FreeMarkerExportUtil(request);
		wg.createDoc(dataMap, fileName,fileType, templateName, response);
		
		return null;
	}
	/**
	 * @param mapping
	 * @param baseForm
	 * @param request
	 * @param response
	 * @throws Exception
	 * 退休员工死亡待遇申请      导出申报表excel
	 */
	@SuppressWarnings("static-access")
	public ActionForward exportRdeathExcel(ActionMapping mapping, ActionForm baseForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取参数信息
		User user = (User) request.getSession().getAttribute("User");
		String userId = user.getUserId();
		
		String ids = request.getParameter("value");
		String[] arr = ids.split("-");
		String businessId = arr[1];
		String winsuredType = arr[2];
		String period = request.getParameter("dataPeriod");
		
		ResultFlagMsg rfm = service.getExportRDEATHData(userId, period, businessId, winsuredType);
		List list = (List) rfm.getResult();
		InsurancePersonRdeathOutWordVO v = (InsurancePersonRdeathOutWordVO) list.get(0);
		
		Map<String,Object> dataMap=new HashMap<String,Object>(); 
		//所有key值必须和ftl模板中${}中字段值对应一样
		dataMap.put("empName",COMMON.isEmpty(v.getEmp_name())?"":v.getEmp_name());						//离退休     人员姓名
		dataMap.put("sex",COMMON.isEmpty(v.getSex())?"":v.getSex());									//性别
		dataMap.put("nation",COMMON.isEmpty(v.getNation())?"":v.getNation());							//民族
		dataMap.put("empNumber",COMMON.isEmpty(v.getPersonal_number())?"":v.getPersonal_number());		//个人编号
		dataMap.put("birDate",COMMON.isEmpty(v.getDate_of_birth())?"":v.getDate_of_birth());			//出生日期
		dataMap.put("retireeDate",COMMON.isEmpty(v.getRetirement_date())?"":v.getRetirement_date());	//离退休 日期
		dataMap.put("dieDate",COMMON.isEmpty(v.getDeath_date())?"":v.getDeath_date());					//死亡时间
		
		//选项部分   1为选择  0为不选
		//参保状态
		String insuredStatus = v.getInsured_status();
		int inSur 	 = 0;
		int breakNum = 0;
		int others   = 0;
		if ("1".equals(insuredStatus)) {
			inSur=1;
		} else if("9".equals(insuredStatus)){
			breakNum=1;
		} else if("2".equals(insuredStatus)){
			others=1;
		}
		dataMap.put("inSur",inSur);			//在保
		dataMap.put("break",breakNum);		//中断
		dataMap.put("others",others);		//其他
		//离退休类别
		String retireeType = v.getRetire_type();
		int leave 	= 0;
		int retiree = 0;
		int quit 	= 0;
		if ("1".equals(retireeType)) {
			leave=1;
		} else if("2".equals(retireeType)){
			retiree=1;
		} else if("3".equals(retireeType)){
			quit=1;
		}
		dataMap.put("leave",leave);			//离休
		dataMap.put("retiree",retiree);		//退休
		dataMap.put("quit",quit);			//退职
		//死亡原因
		String deathReason = v.getDeath_of_reason();
		int ill = 0;
		int job = 0;
		if ("1".equals(deathReason)) {
			job=1;
		} else if("2".equals(deathReason)){
			ill=1;
		} 
		dataMap.put("ill",ill);			//因病非因工
		dataMap.put("job",job);			//因工
		//丧葬方式
		String funeralStyle = v.getFuneral_style();
		int fire 	  = 0;
		int noFire    = 0;
		int soil 	  = 0;
		int fireSoil  = 0;
		int dieOthers = 0;
		if ("1".equals(funeralStyle)) {
			fire=1;
		} else if("2".equals(funeralStyle)){
			soil=1;
		} else if("3".equals(funeralStyle)){
			fireSoil=1;
		} else if("4".equals(funeralStyle)){
			noFire=1;
		} else if("9".equals(funeralStyle)){
			dieOthers=1;
		}
		
		dataMap.put("fire",fire);				//火葬
		dataMap.put("noFire",noFire);			//非火化区土葬  
		dataMap.put("soil",soil);				//少数民族土葬 
		dataMap.put("fireSoil",fireSoil);		//火化区土葬
		dataMap.put("dieOthers",dieOthers);		//其他 
		
		dataMap.put("monAnnuity",COMMON.isEmpty(
				v.getMonthly_pension())?"":v.getMonthly_pension());	//月发养老金(元)
		
		String fileName = "Rdeath";						//文件名称
		String fileType = "xls";						//文件扩展名
		String templateName = "insurancePersonRdeath";	//文件模板名称
		
		// 调用工具类的createDoc方法生成Word文档  
		// 参数      （数据源，文档名称，模板名称，response）
		FreeMarkerExportUtil  wg = new FreeMarkerExportUtil(request);
		wg.createDoc(dataMap, fileName, fileType,templateName, response);
		
		return null;
	}
}
