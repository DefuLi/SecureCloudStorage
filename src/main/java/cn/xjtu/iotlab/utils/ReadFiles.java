package cn.xjtu.iotlab.utils;

import cn.xjtu.iotlab.service.FilesManagerService;
import cn.xjtu.iotlab.service.impl.FilesManagerServiceImpl;
import cn.xjtu.iotlab.vo.Files;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

@Data
public class ReadFiles {

    int id = 0;

    @Autowired
    FilesManagerServiceImpl filesManagerService;

    /**
     * 将某用户下的所有文件读入数据库
     * @param path 用户文件夹路径
     * @throws FileNotFoundException 文件未找到
     * @throws IOException io异常
     */
    public void readAllFiles(String path, String userName) throws FileNotFoundException, IOException{
        File file = new File(path);
        System.out.println(path + "/" + userName);
        //不是文件夹，错误
        if(!file.isDirectory()){
            System.out.println("file is :" + file.getName());
            System.out.println("该文件不是文件夹，错误！");
        }else if(file.isDirectory()){//根目录是文件夹，正确
            String [] fileList = file.list();
            System.out.println(fileList.length);
            //遍历根目录所有文件
            for(String temp:fileList){
//                Files files = null;
                System.out.println(temp);
                String tempPath = path + "/" + temp;
                File tempFile = new File(tempPath);//当前文件
                Files files = valuesToFile(tempFile, userName, 0);
                System.out.println(files);
                int x = filesManagerService.insertFiles(files);
                if(tempFile.isDirectory()){
                    readFiles(path + "/" + temp,userName,files.getId());
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
        int x = filesManagerService.insertFiles(temp);
        parentIdNow = temp.getId();
        //递归访问文件夹
        if(file.isDirectory()){
            String [] fileList = file.list();
            for(String t:fileList)
            readFiles(path + "/" + t, userName,parentIdNow);
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
        temp.setId(id++);//修改id
        temp.setName(name);//文件名
        temp.setCreateUserName(userName);//创建用户名
        temp.setEditBy(userName);//修改用户名
        temp.setDescribe(null);//描述
        Long lastModified = file.lastModified();
        Date editDate = new Date(lastModified);
        temp.setCreateTime(editDate);//文件创建时间
        temp.setCreateTime(editDate);//文件修改时间
        temp.setParentId(parentId);//设置父目录
        temp.setSize((int)file.length());//文件大小
        temp.setSuffixName(name.substring(name.lastIndexOf("."),name.length()));//文件后缀名
        temp.setType(2);//文件图标类型
        temp.setFileType(3);//文件类型
        return temp;
    }
}
