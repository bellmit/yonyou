package com.yonyou.dms.web.contorller.fileupload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.f4.filestore.FileStoreException;

@Controller
@RequestMapping("/upload")
public class MultiJpgUploader {

    @Resource(name="fileStoreService")
    FileStoreService fileStoreService;
    
    public FileStoreService getFileStoreService() {
        return fileStoreService;
    }

    public void setFileStoreService(FileStoreService fileStoreService) {
        this.fileStoreService = fileStoreService;
    }
    
    private void testFileStore(String fileid) throws FileStoreException{
        byte[] fsresult=fileStoreService.readFileByteByFileId(fileid);
        System.out.println("filebytes:"+fsresult.length);
    }
    
    private String saveFile(HttpServletRequest request,MultipartFile file) {  
        // 判断文件是否为空  
        if (!file.isEmpty()) {
            try {  
                // 文件保存路径  
                // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\img\\upload\\文件夹中
//              String realPath = request.getServletContext().getRealPath("/img/upload");
                String storePath="e://tmp/";
                // 转存文件  
                FileUtils.copyInputStreamToFile(file.getInputStream(),  new File(storePath, file.getOriginalFilename()));  
                System.out.println("--------文件存储到:"+storePath);
                String fileId=fileStoreService.writeFile(file);
                System.out.println("fileid:"+fileId);
                
                return fileId;  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return "";  
    }  
    
    @RequestMapping(value = "/uploadjpg",method = RequestMethod.POST)
    @ResponseBody
    public String uploadimg(HttpServletRequest request, @RequestParam("imageCount") Integer imageCount, @RequestParam("id") String id,
            @RequestParam("desc") String desc,@RequestParam("image") MultipartFile[] files) throws FileStoreException {
        System.out.println("收到客户端请求....");
        String fileID="";
        if (null != files) {
            if (files.length==0) {
                System.out.println("文件未上传");
            } else {
                System.out.println("文件数量: " + files.length);
                System.out.println("========================================");
                
                //循环获取file数组中得文件  
                for(int i = 0;i<files.length;i++){  
                    MultipartFile file = files[i];  
                    //保存文件 
                    System.out.println("准备保存第"+String.valueOf(i+1)+"个文件");
                    fileID = saveFile(request,file);  
                }
                if(desc!=null && desc.length()>0){
                    testFileStore(desc);
                }
                return fileID;
            }
        }
        return fileID;
    }
    
    @RequestMapping(value="/querysessionid",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySessionId(HttpServletRequest request,HttpSession httpSession){
        Map<String, Object> dict = new HashMap<String, Object>();
        dict.put("sessionid", request.getSession().getId());
        return dict;
    }
    
    
    @RequestMapping(value="/getfile",method = RequestMethod.GET)
    @ResponseBody
    public String getFile(HttpServletRequest request,HttpServletResponse response){
          // response.setContentType("image/*");  
        ByteArrayInputStream bis = null;  
        OutputStream os = null;  
        try {  
            bis = new ByteArrayInputStream(fileStoreService.readFileByteByFileId("\\BucketNode1\\d03\\20170328\\BucketNode1\\d03\\20170328\\4cdaeec5-8427-44c3-8168-312ef3277e30.jpg"));  
            os = response.getOutputStream();  
            int count = 0;  
            byte[] buffer = new byte[1024 * 8];  
            while ((count = bis.read(buffer)) != -1) {  
                os.write(buffer, 0, count);  
                os.flush();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        try {  
            bis.close();  
            os.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return "ok";  
    }
    
    public static void main(String[] args) throws Exception {
        String paths[] = {"applicationContext.xml"};
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        FileStoreService fileStoreService = (FileStoreService)ctx.getBean("fileStoreService");
        
        
        byte[] fsresult=fileStoreService.readFileByteByFileId("/BucketNode1/localeDataNode2/20170327/020d007a-6404-4c4f-8b16-247c68907642.jpg");
        System.out.println(fsresult.length);
    }
    


}
