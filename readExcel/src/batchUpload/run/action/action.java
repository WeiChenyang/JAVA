package batchUpload.run.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import batchUpload.batchUtils.BaseAction;
import batchUpload.batchUtils.ExcelUtil;
import batchUpload.run.service.UpService;
import batchUpload.run.vo.User;

@SuppressWarnings("serial")
public class action extends HttpServlet{
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		User user = new User();
		BaseAction payAction = new BaseAction();
		List fileList = payAction.uploadFiles(request,user,false);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + fileList);
		if (fileList == null || fileList.size() == 0) {
			payAction.filterResponseCharset(response);
			response.getWriter().write(
					"{success:false,validate:false"
							+ ",batchId:'',msg:'上传文件失败,未找到上传文件.'}");
			System.out.println("上传失败");
		}
		File file = (File) fileList.get(0);
		// 解析上传的Excel文件，与当前类匹配（upload-mapping.xml），并返回一个集合
		List<User> list = null;
		try {
			list = ExcelUtil.readExcelPatch(file, Class.forName(User.class.getName()), 2);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		for (User user2 : list) {
			System.out.println(user2.getUserId() + "," + user2.getUserName() + "," + user2.getSex() + "," + user2.getAge() + "," + user2.getBirthday());
		}
		String msg = UpService.upload(list);
		if (list == null || list.size() == 0) {
			msg = "上传文件失败,不能正确解析文件,请检查文件及格式.";
		}
		payAction.filterResponseCharset(response);
		response.getWriter().write(
				"{success:false, validate:false"
						+ ", ''msg:'"+msg+"'}");
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req,resp);
	}
}