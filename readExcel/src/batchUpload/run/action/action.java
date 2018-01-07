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
							+ ",batchId:'',msg:'�ϴ��ļ�ʧ��,δ�ҵ��ϴ��ļ�.'}");
			System.out.println("�ϴ�ʧ��");
		}
		File file = (File) fileList.get(0);
		// �����ϴ���Excel�ļ����뵱ǰ��ƥ�䣨upload-mapping.xml����������һ������
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
			msg = "�ϴ��ļ�ʧ��,������ȷ�����ļ�,�����ļ�����ʽ.";
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