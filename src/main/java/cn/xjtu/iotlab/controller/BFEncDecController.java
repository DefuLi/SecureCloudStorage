package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.impl.CertServiceImpl;
import cn.xjtu.iotlab.service.impl.FilesManagerServiceImpl;
import cn.xjtu.iotlab.utils.encdec.FileSearch;
import cn.xjtu.iotlab.utils.encdec.TestBF;
import cn.xjtu.iotlab.vo.BFFile;
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
import java.util.Date;
import java.util.List;

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
    //文件加密,将本地选中传入的文件进行BF加密
    @ResponseBody
    @RequestMapping(value = "/bfencrypt", method = RequestMethod.POST)
    public int BFEncrypt(@RequestParam("file") MultipartFile multipartFile , @RequestParam("username") String username) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        TestBF bf = new TestBF();
        System.out.println(username);
        String signal= bf.filebfencrpt(file,username);
        //前端
        //保存
        //上传
        //取消
        if(signal != null)
        return 1;
        else{
            return 0;
        }
    }

    //本地上传BF加密文件解密功能
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

    //云端上传文件进行加密的BF云端加密功能
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
        String signal= bf.filebfencrpt(file,LoginUserName);
        if(signal !=null) {
            System.out.println(signal);
            File f = new File(signal);
            int PID = filesManagerService.getBFencPID(LoginUserName);
            Files files = valuesToFile(f, LoginUserName,PID);//插入数据库
            filesManagerService.insertFiles(files);
            String userName = LoginUserName;
            String fileName = f.getName();
            int rand =10086;
            FileSearch fileSearch = new FileSearch(userName);
            // System.out.println(fileName);
            System.out.println(userName);
            fileSearch.random = Integer.toString(rand);
            List<String> BFValues = fileSearch.keywordSplitByFileName(FileName,rand);
            int id = filesManagerService.getIdByName(fileName,userName,PID);
            for(String bfValue: BFValues){
                filesManagerService.insertFilesBF(new BFFile(id, fileName, bfValue));
                System.out.println(bfValue);
            }
            return "success" + FileName;
        }
        else{

            return "failed";
        }
    }

    //BF云端加密文件进行解密功能模块
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
            int PID = filesManagerService.getBFdecPID(LoginUserName);
            Files files = valuesToFile(f, LoginUserName,PID);//插入数据库
            filesManagerService.insertFiles(files);
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
    public Files valuesToFile(File file,String userName, int parentId){
        int id = filesManagerService.getMaxId();
        System.out.println(id);
        Files temp = new Files();
        String suffixName;
        String name = file.getName();
        temp.setId(id+1);
        temp.setName(name);//文件名
        temp.setCreateUserName(userName);//创建用户名
        temp.setEditBy(userName);//修改用户名
        temp.setDescribe(null);//描述
        Long lastModified = file.lastModified();
        Date editDate = new Date(lastModified);
        temp.setCreateTime(editDate);//文件创建时间
        temp.setEditTime(editDate);//文件修改时间
        temp.setParentId(parentId);//设置父目录
        temp.setSize((int)file.length());//文件大小
        System.out.println(name + ":" + file.isDirectory());
        if(file.isDirectory()){
            suffixName = "";
            temp.setSuffixName(null);
        }else{
            suffixName = name.substring(name.lastIndexOf("."));
            temp.setSuffixName(suffixName);//文件后缀名
        }
//        temp.setSuffixName("txt");
        temp.setType(getFileType(suffixName));//文件图标类型
        temp.setFileType(3);//文件类型
        return temp;
    }

    /**
     * 根据文件后缀名返回文件类型，1是文件夹
     * @param suffixName 后缀名
     * @return type
     */
    public int getFileType(String suffixName){
        String suffix = suffixName.toLowerCase();
        if(suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("png")){
            return 2;
        }else if(suffix.equals("")){
            return 1;
        }else if(suffix.equals("mp4")){
            return 3;
        }else{
            return 4;
        }
    }

}
