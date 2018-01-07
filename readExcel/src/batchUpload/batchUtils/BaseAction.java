package batchUpload.batchUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import batchUpload.run.vo.User;

public class BaseAction {
	
	private static int maxMemorySize = 1024*1024;
	
	private static int maxUploadSize = 1024*1024*10;
	
	public void filterResponseCharset(HttpServletResponse response){
		response.setContentType("charset=UTF-8");
		response.addHeader("Content-type", "text/html;charset=UTF-8");
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List uploadFiles(HttpServletRequest request, User form, boolean ignoreFormField){
		List list = new ArrayList();
		try{
			//ÉÏ´«Â·¾¶
			String tempUploadDir = new SystemProperties().getUploadLogPath();
			//String tempUploadDir = "E:/arch/eapSave/";
			List fileList =null;
			/*if(form instanceof BaseModel){
				BaseModel model = (BaseModel) form;
				model.getFile();
				
				return getFileList(model.getFile(),tempUploadDir);
			}else{*/
				
				File tempDirectory = new File(tempUploadDir);
				tempDirectory.mkdirs();
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(maxMemorySize);
				factory.setRepository(tempDirectory);
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(maxUploadSize);
				fileList =  upload.parseRequest(request);
				
				if(fileList!=null && fileList.size()>0){
					Iterator it = fileList.iterator();
					while(it.hasNext()){
						FileItem item = (FileItem) it.next();
//						if(!item.isFormField()){
							String fileName = item.getName();
							if(StringUtils.isNotBlank(fileName)){
								String extfile = fileName.substring(fileName.indexOf("."));
								SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");   
								String pfileName = fmt.format(new Date()).toString().trim();
								File file = new File(tempUploadDir + pfileName + extfile);
								item.write(file);
								list.add(file);
							}
//						}else{
							if(!ignoreFormField){
								//Set normal form filed values to attribute.
								//request.setAttribute(item.getFieldName(), item.getString());
								System.out.println(ignoreFormField);
							}
//						}
					}
				}
			//}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
}

