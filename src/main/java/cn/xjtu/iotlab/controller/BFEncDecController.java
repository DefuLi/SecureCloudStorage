package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.impl.CertServiceImpl;
import cn.xjtu.iotlab.service.impl.FilesManagerServiceImpl;
import cn.xjtu.iotlab.utils.encdec.TestBF;
import cn.xjtu.iotlab.vo.Files;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * BF 文件加解密模块
  */
@RestController
@RequestMapping("bf")
public class BFEncDecController {

    @Autowired
    FilesManagerServiceImpl filesManagerService;
    @Autowired
    CertServiceImpl certService;
    //文件加密
    @ResponseBody
    @RequestMapping(value = "/bfencrypt", method = RequestMethod.POST)
    public int BFEncrypt(@RequestParam("file") MultipartFile multipartFile , @RequestParam("username") String username) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        TestBF bf = new TestBF();
        System.out.println(username);
        boolean signal= bf.filebfencrpt(file,username);
        //前端
        //保存
        //上传
        //取消
        if(signal == true)
        return 1;
        else{
            return 0;
        }
    }

    //文件解密
    @ResponseBody
    @RequestMapping(value = "/bfdecrypt", method = RequestMethod.POST)
    public String BFDecrypt(@RequestParam("file") MultipartFile multipartFile , @RequestParam("username") String username,HttpServletResponse response) throws IOException, BadPaddingException, IllegalBlockSizeException {
        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        TestBF bf = new TestBF();
        System.out.println(username);
        String signal= bf.filebfdecrpt(file,username);
        File f = new File(signal);
        String fileName = f.getName();
        System.out.println(fileName);
        String a = fileName.substring(fileName.indexOf("."));
        System.out.println(a);
        response.setContentType(a);
        //response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", " attachment;filename=" + fileName);
        response.setContentLength((int) f.length());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            byte[] buffer = new byte[128];
            int count = 0;
            while ((count = fis.read(buffer)) > 0) {
                response.getOutputStream().write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getOutputStream().flush();
            response.getOutputStream().close();
            fis.close();
        }


        //前端
        //保存
        //取消
        return a;
    }

    //文件上传
    @ResponseBody
    @RequestMapping(value = "/bfupload", method = RequestMethod.POST)
    public String upload(@RequestParam("CreateUserName") String CreateUserName, @RequestParam("ParentId") int ParentId,@RequestParam("LoginUserName") String LoginUserName,@RequestParam("FileName") String FileName){
        if(FileName.indexOf(".")==-1) return "Folder";
        String basePath = "src/main/resources/iotlab/" + CreateUserName;
        String Path = basePath + getFilePath(ParentId,CreateUserName)+"/"+FileName;
        File file = new File(Path);
        System.out.println("----------------------");
//        System.out.println(file.getPath());
        if(!file.exists()) return "nofile";
        System.out.println(Path);
        System.out.println(CreateUserName);
        System.out.println(LoginUserName);
        if(!CreateUserName.equals(LoginUserName)){
          if(certService.findCert(LoginUserName,CreateUserName)==false) return "nocert";
        }
        TestBF bf = new TestBF();
        boolean signal= bf.filebfencrpt(file,LoginUserName);
        if(signal == true)
            return "success"+FileName;
        else{

            return "failed";
        }
    }

    //文件下载
    @ResponseBody
    @RequestMapping(value = "/bfdownload", method = RequestMethod.POST)
    public String download(@RequestParam("CreateUserName") String CreateUserName, @RequestParam("ParentId") int ParentId,@RequestParam("LoginUserName") String LoginUserName,@RequestParam("FileName") String FileName) throws BadPaddingException, IllegalBlockSizeException, IOException {
        if(FileName.indexOf(".")==-1) return "Folder";
        String basePath = "src/main/resources/iotlab/" + CreateUserName;
        String Path = basePath + getFilePath(ParentId,CreateUserName)+"/"+FileName;
        File file = new File(Path);
        System.out.println("----------------------");
//        System.out.println(file.getPath());
        if(!file.exists()) return "nofile";
        System.out.println(Path);
        System.out.println(CreateUserName);
        System.out.println(LoginUserName);
        if(!CreateUserName.equals(LoginUserName)){
            if(certService.findCert(LoginUserName,CreateUserName)==false) return "nocert";
        }
        TestBF bf = new TestBF();
        String signal= bf.filebfdecrpt(file,LoginUserName);
        if(signal == null)
            return "failed";
        else{
            File f = new File(signal);
            String fileName = f.getName();
            return "success"+fileName;
        }
    }

    public String getFilePath(int parentId, String userName){
//        System.out.println("parent id: " + parentId);
        int tempId = parentId;
        StringBuilder tempPath = new StringBuilder();
        while(tempId != 0){
            Files tempFile = filesManagerService.searchById(tempId);
            tempPath.insert(0,"/" + tempFile.getName());
            tempId = tempFile.getParentId();
        }
        return tempPath.toString();
    }

}
