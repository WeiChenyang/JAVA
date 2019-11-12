package com.pccw.business.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Administrator
 * 获取模板，生成word
 */
public class FreeMarkerExportUtil {
	private static Configuration configuration = null; 
    public FreeMarkerExportUtil() {    
    }  
    public FreeMarkerExportUtil(HttpServletRequest request) {  
        configuration = new Configuration();  
        configuration.setDefaultEncoding("utf-8");  
        //获取模板所在路径
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/template/ftl");
		try {
			configuration.setDirectoryForTemplateLoading(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
    } 
    /**
     * @param dataMap
     * @param fileName
     * @param templateName
     * @return
     * 将数据写入到file中
     */
    public static void createDoc(Map<?, ?> dataMap,String fileName,String fileType,String templateName,HttpServletResponse response) {  
        File f = new File(fileName+"."+fileType); 
        try { 
        	//获取指定模板
        	Template t = configuration.getTemplate(templateName +".ftl"); 
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");  
            t.process(dataMap, w);  
            w.close();  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            throw new RuntimeException(ex);  
        }
        InputStream fin = null;
        ServletOutputStream out = null;
        try {  
        	fin = new FileInputStream(f);  
              
            response.setCharacterEncoding("utf-8");  
            response.setContentType("application/msword");  
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc  
            response.addHeader("Content-Disposition", "attachment;filename="+fileName+"."+fileType);  
              
            out = response.getOutputStream();  
            // 缓冲区  
            byte[] buffer = new byte[512];  
            int bytesToRead = -1;  
            // 通过循环将读入的Word文件的内容输出到浏览器中  
            while((bytesToRead = fin.read(buffer)) != -1) {  
                out.write(buffer, 0, bytesToRead);  
            }  
        } catch (IOException e) {
			e.printStackTrace();
		} finally {  
            try {
				if(fin != null) fin.close();  
				if(out != null) out.close();  
				// 删除临时文件  
				if(f != null) f.delete(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
    }  
  
    
}
