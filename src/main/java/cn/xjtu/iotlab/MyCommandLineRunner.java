package cn.xjtu.iotlab;

import cn.xjtu.iotlab.service.impl.FilesManagerServiceImpl;
import cn.xjtu.iotlab.vo.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    Set<Integer> idSet;
    int id = 0;
    @Autowired
    FilesManagerServiceImpl filesManagerService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("后端已启动,数据已添加");
        idSet = filesManagerService.getAllId();
        System.out.println(idSet.size());
        String path = "src/main/resources/iotlab";
//            String userName = "admin";
//        path += userName;
//        System.out.println(path);
        try {
            readAllFiles(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @PreDestroy
    public void destroy(){
        System.out.println("后端关闭后执行，数据以删除");
        filesManagerService.deleteAll();
    }

    /**
     * 将某用户下的所有文件读入数据库
     * @param path 用户文件夹路径
     * @throws FileNotFoundException 文件未找到
     * @throws IOException io异常
     */
    public void readAllFiles(String path) throws FileNotFoundException, IOException{
        File rootFile = new File(path);
        System.out.println(rootFile.getAbsolutePath());
        String [] userNames = rootFile.list();
        if(userNames == null){
            System.out.println("无用户文件");
            return;
        }
        for(String userName:userNames){
            String userPath = path + '/' + userName;
            System.out.println("userPath: " + userPath);
            File file = new File(userPath);

            //不是文件夹，错误
            if(!file.isDirectory()){
                System.out.println("file is :" + file.getName());
                System.out.println("该文件不是文件夹，错误！");
            }else if(file.isDirectory()){//根目录是文件夹，正确
                String [] fileList = file.list();
                //遍历根目录所有文件
                for(String temp:fileList){
                    Files files = null;
                    String tempPath = userPath + '/' + temp;
                    System.out.println("tempPath: " + tempPath);
                    File tempFile = new File(tempPath);//当前文件
                    files = valuesToFile(tempFile, userName, 0);
//                System.out.println(files);
                    if(files == null) continue;
                    System.out.println(files);
                    filesManagerService.insertFiles(files);
//                System.out.println(files);
//                System.out.println(files.getId());
                    if(tempFile.isDirectory()){
                        readFiles(tempPath,userName,files.getId(),0);
                    }
                }
            }
        }

    }

    /**
     * 递归将文件夹中的所有文件写入数据库
     * @param path 文件路径
     * @param userName 用户名
     * @param parentId 父目录id
     * @param flag 判断是不是根目录的文件夹
     */
    public void readFiles(String path, String userName, int parentId, int flag){
        File file = new File(path);

        int parentIdNow;
//        Files temp = valuesToFile(file, userName, parentId);
        Files temp = null;
        if(file.isDirectory() && flag == 0){
            temp = filesManagerService.searchById(id);
        }else{
            temp = valuesToFile(file, userName, parentId);
        }
//        System.out.println(temp);
        if(temp == null){
            return;
        }
        if(flag != 0){
            filesManagerService.insertFiles(temp);
        }
        parentIdNow = temp.getId();
        //递归访问文件夹
        if(file.isDirectory()){
            String [] fileList = file.list();
            System.out.println("path :" + path);
            for(String t:fileList){
                System.out.println(path + '/' + t);
                readFiles(path + '/' + t, userName,parentIdNow,1);
            }
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
//            System.out.println("values to file" + name);
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
