package cn.xjtu.iotlab.controller;


import cn.xjtu.iotlab.service.FilesManagerService;
import cn.xjtu.iotlab.service.impl.FilesManagerServiceImpl;
import cn.xjtu.iotlab.utils.ReadFiles;
import cn.xjtu.iotlab.utils.encdec.FileSearch;
import cn.xjtu.iotlab.vo.BFFile;
import cn.xjtu.iotlab.vo.Files;
import com.alibaba.fastjson.JSONObject;
import javafx.beans.binding.ObjectExpression;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.cs.ext.SJIS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import static org.springframework.boot.web.servlet.server.Encoding.DEFAULT_CHARSET;

@RestController
@Controller
@RequestMapping("/fileManager")
@CrossOrigin
public class FileManagerController {
//    int rand=(int)(Math.random()*10000);
    int rand = 10086;
    Set<Integer> idSet;
    int id = 0;
    @Autowired
    FilesManagerServiceImpl filesManagerService;

    @RequestMapping(value = "/getFileList", method = RequestMethod.POST)
    @ResponseBody
    public Object getFileList(HttpServletRequest req, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String userName = req.getParameter("userName");
//        System.out.println(userName);
        jsonObject.put("Message", "操作成功");
        jsonObject.put("StatusCode",200);
        jsonObject.put("CallbackType", null);
        jsonObject.put("Data", filesManagerService.getRootFile(userName));

        return jsonObject;
    }

    @RequestMapping(value = "/getId" , method = RequestMethod.GET)
    @ResponseBody
    public Object getId(){
        System.out.println("123");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Data", filesManagerService.getAllId());
        return jsonObject;
    }

    @RequestMapping(value = "/getAllFoldersApi")
    public Object getAllFoldersApi(HttpServletRequest req, HttpSession session){
        String userName = req.getParameter("userName");
//        System.out.println(userName);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Message", "操作成功");
        jsonObject.put("StatusCode",200);
        jsonObject.put("CallbackType", null);
        jsonObject.put("Data", filesManagerService.getAllFileList(userName));

        return jsonObject;
    }

    @RequestMapping(value = "/getFilesByNameApi", method = RequestMethod.POST)
    @ResponseBody
    public Object getFilesByNameApi(HttpServletRequest req, HttpSession session){
        List<Files> targetFiles = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        String BFFileName = req.getParameter("fileName");
        String fileName = req.getParameter("fileName");
        int type = Integer.parseInt(req.getParameter("type"));
        String CreateUsername = req.getParameter("userName");

        jsonObject.put("Message", "操作成功");
        jsonObject.put("StatusCode",200);
        jsonObject.put("CallbackType", null);

        if(fileName.equals("")){
            jsonObject.put("Data", filesManagerService.getAllId());
            return jsonObject;
        }

        fileName = "%" + fileName + "%";
        //BF搜索
        FileSearch fileSearch = new FileSearch(CreateUsername);
        List<String> searchKwBF = fileSearch.searchKeywordSplit(BFFileName,rand);//将搜索关键字划分为BF值
        //明文局部搜索
        if(type == 1){
            targetFiles = filesManagerService.getFilesByNameLocal(fileName, CreateUsername);
            System.out.println(targetFiles.size());
        }
        //明文全局搜索
        else if(type == 2){
            targetFiles = filesManagerService.getFilesByNameGlobal(fileName);
            System.out.println(targetFiles.size());
        }
        //BF局部搜索
        else if(type == 3){
            Set<Integer> ids = new HashSet<>();
            for(String searchBF:searchKwBF){
                List<Files> temp = filesManagerService.getFilesByBFLocal(searchBF, CreateUsername);
                for(Files f:temp){
                    if(!ids.contains(f.getId())){
                        targetFiles.add(f);
                        ids.add(f.getId());
                    }
                }
            }
        }
        //BF全局搜索
        else{
            Set<Integer> ids = new HashSet<>();
            for(String searchBF:searchKwBF){
                List<Files> temp = filesManagerService.getFilesByBFGlobal(searchBF);
                for(Files f:temp){
                    if(!ids.contains(f.getId())){
                        targetFiles.add(f);
                        ids.add(f.getId());
                    }
                }
            }
        }

        jsonObject.put("Data", targetFiles);
        return jsonObject;
    }

//    @RequestMapping(value = "/saveBFValue", method = RequestMethod.POST)
//    public void saveBFValue(HttpServletRequest req, HttpSession session){
////        String fileName = req.getParameter("fileName");
////        String userName = req.getParameter("userName");
//        String fileName = "西安新一线城市.txt";
//        String userName = "admin";
//        FileSearch fileSearch = new FileSearch(userName);
//        System.out.println(fileName);
//        System.out.println(userName);
//        fileSearch.random = Integer.toString(rand);
//        List<String> BFValues = fileSearch.keywordSplitByFileName(fileName,rand);
//        int id = filesManagerService.getIdByName(fileName,userName);
//        for(String bfValue: BFValues){
//            filesManagerService.insertFilesBF(new BFFile(id, fileName, bfValue));
//            System.out.println(bfValue);
//        }
//    }


    //判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object fileManagerPage(@RequestParam("userName") String userName, @RequestParam("file") MultipartFile multipartFile, @RequestParam("pathId") String pathId, @RequestParam("parentPathId") String parentPathId, HttpServletRequest req, HttpSession session) throws IOException {
        System.out.println("userName: " + userName);
        String basePath = "src/main/resources/iotlab/" + userName;
        List<Integer> results = new ArrayList<>();
        System.out.println(pathId);
        String path = basePath + getFilePath(Integer.parseInt(pathId), userName) + "/" + multipartFile.getOriginalFilename();
        System.out.println(path);
//        System.out.println(path + multipartFile.getOriginalFilename());
//        String directory = getDirectoryByPathId(pathId);

        //设置一个file的地址，和multipartFile.getOriginalFilename()进行拼接就行
        java.io.File file = new File(path);

        //将multipartFile转换成本地File文件
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        //移动文件

        Files files = valuesToFile(file, userName, Integer.parseInt(pathId));//插入数据库
        filesManagerService.insertFiles(files);
        return "success";
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    @ResponseBody
    public Object downloadFile(HttpServletRequest req, HttpSession session, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("fileInfo");
        System.out.println(id);
        Files files = filesManagerService.searchById(Integer.parseInt(id));
        String basePath = "src/main/resources/iotlab/" + files.getCreateUserName();
        String path = basePath + getFilePath(files.getParentId(), files.getCreateUserName()) + "/" + files.getName();
        System.out.println(path);
        File file = new File(path);
//        String absolutePath = file.getAbsolutePath();

        download(file, resp);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Message", "操作成功");
//        jsonObject.put("StatusCode",200);
//        jsonObject.put("CallbackType", null);
//        jsonObject.put("headers",files.getName());
//        jsonObject.put("data", file.getAbsoluteFile());
//
//
//        return jsonObject;
        return null;
    }

    /**
     * 根据parentId获取文件相对路径
     * @param parentId 父目录id
     * @param userName 用户名
     * @return 文件的相对路径
     */
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

//    public void insertFileToDB(File file, int parentId){
//        int id = filesManagerService.getMaxId();
//        System.out.println(id);
//        Files files = new Files();
//        files.setId(id);
//        files.setParentId(parentId);
//
//    }

    /**
     * 给Files赋值
     * @param file 当前遍历到的文件
     * @param userName 用户名
     * @return Files对象
     */
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

    public void download(File file, HttpServletResponse response) throws IOException {

        //下载后的文件名
        String fileName = file.getName();
        if (file.exists()) {
//            System.out.println("--------");
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
//             下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Content-Length",""+file.length());
//             实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
//                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    response.getOutputStream().write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("文件下载完成！！");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                response.getOutputStream().flush();
                response.getOutputStream().close();
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void setResponse(String fileName, HttpServletResponse response) {
        // 清空输出流
        response.reset();
        response.setContentType("application/x-download;charset=GBK");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
