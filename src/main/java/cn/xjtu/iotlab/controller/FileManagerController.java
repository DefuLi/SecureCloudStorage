package cn.xjtu.iotlab.controller;


import cn.xjtu.iotlab.service.FilesManagerService;
import cn.xjtu.iotlab.service.impl.FilesManagerServiceImpl;
import cn.xjtu.iotlab.utils.ReadFiles;
import cn.xjtu.iotlab.vo.Files;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.cs.ext.SJIS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RestController
@Controller
@RequestMapping("/fileManager")
public class FileManagerController {
    Set<Integer> idSet;
    int id = 0;
    @Autowired
    FilesManagerServiceImpl filesManagerService;

    @RequestMapping(value = "/getAllFileList", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllFileList(HttpServletRequest req, HttpSession session){
        String path = "/Users/wangchenyu/iotlab/admin";
//        String userName = "admin";
        String userName = req.getParameter("userName");
        System.out.println("-----------------------");
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Message", "操作成功");
        jsonObject.put("StatusCode",200);
        jsonObject.put("CallbackType", null);
        jsonObject.put("Data", filesManagerService.getAllFileList(userName));

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

    @RequestMapping(value = "/saveLocalFile")
    public void saveLocalFile(HttpServletRequest req, HttpSession session){
        idSet = filesManagerService.getAllId();
        System.out.println(idSet.size());
        String path = "/Users/wangchenyu/iotlab/";
        String userName = "admin";
        path += userName;
        try {
            readAllFiles(path,userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 将某用户下的所有文件读入数据库
     * @param path 用户文件夹路径
     * @throws FileNotFoundException 文件未找到
     * @throws IOException io异常
     */
    public void readAllFiles(String path, String userName) throws FileNotFoundException, IOException{
        File file = new File(path);
        //不是文件夹，错误
        if(!file.isDirectory()){
            System.out.println("file is :" + file.getName());
            System.out.println("该文件不是文件夹，错误！");
        }else if(file.isDirectory()){//根目录是文件夹，正确
            String [] fileList = file.list();
            //遍历根目录所有文件
            for(String temp:fileList){
                Files files = null;
                String tempPath = path + '/' + temp;
                File tempFile = new File(tempPath);//当前文件
                files = valuesToFile(tempFile, userName, 0);
                System.out.println(files);
                if(files == null) continue;
                System.out.println(files);
                filesManagerService.insertFiles(files);
//                System.out.println(files);
//                System.out.println(files.getId());
                if(tempFile.isDirectory()){
                    readFiles(path + '/' + temp,userName,files.getId());
                }
            }
        }
    }

    /**
     * 递归将文件夹中的所有文件写入数据库
     * @param path 文件路径
     * @param userName 用户名
     * @param parentId 父目录id
     */
    public void readFiles(String path, String userName, int parentId){
        File file = new File(path);
        int parentIdNow;
        Files temp = valuesToFile(file, userName, parentId);
        if(temp == null){
            return;
        }
        filesManagerService.insertFiles(temp);
        parentIdNow = temp.getId();
        //递归访问文件夹
        if(file.isDirectory()){
            String [] fileList = file.list();
            for(String t:fileList)
                readFiles(path + '/' + t, userName,parentIdNow);
        }
    }

    /**
     * 给Files赋值
     * @param file 当前遍历到的文件
     * @param userName 用户名
     * @return Files对象
     */
    public Files valuesToFile(File file,String userName, int parentId){
        Files temp = new Files();
        String suffixName;
        String name = file.getName();
        if(!idSet.contains(id+1)){
            temp.setId(++id);//id
            idSet.add(id);
            System.out.println("values to file" + name);
        }else{
            System.out.println(idSet.contains(id+1));
            return null;
        }
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
