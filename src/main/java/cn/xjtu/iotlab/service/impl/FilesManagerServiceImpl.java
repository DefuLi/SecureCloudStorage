package cn.xjtu.iotlab.service.impl;

import cn.xjtu.iotlab.dao.FilesManagerMapper;
import cn.xjtu.iotlab.service.FilesManagerService;
import cn.xjtu.iotlab.vo.BFFile;
import cn.xjtu.iotlab.vo.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FilesManagerServiceImpl implements FilesManagerService {
    @Autowired
    FilesManagerMapper filesManagerMapper;

    @Override
    public List<Files> getAllFileList(String userName) {
        return filesManagerMapper.getAllFileList(userName);
    }

    //向数据库中插入文件
    @Override
    public int insertFiles(Files file){
        return filesManagerMapper.insertFiles(file);
    }

    //根据id搜索文件
    @Override
    public Files searchById(int id){
        return filesManagerMapper.searchById(id);
    }

    //获取数据库中所有id
    public Set<Integer> getAllId(){
        return filesManagerMapper.getAllId();
    }

    //删除数据库所有内容
    public void deleteAll(){
        filesManagerMapper.deleteAll();
    }

    //获取用户根目录文件
    public List<Files> getRootFile(String userName){
        return filesManagerMapper.getRootFile(userName);
    }

    //明文局部搜索
    public List<Files> getFilesByNameLocal(String fileName, String userName, String suffixName){
        return filesManagerMapper.getFilesByNameLocal(fileName, userName, suffixName);
    }

    //明文全局搜索
    public List<Files> getFilesByNameGlobal(String fileName, String suffixName){
        return filesManagerMapper.getFilesByNameGlobal(fileName, suffixName);
    }

    //插入文件的BF值
    public void insertFilesBF(BFFile bfFile){
        filesManagerMapper.insertFilesBF(bfFile);
    }

    //根据文件名搜索Id
    public int getIdByName(String fileName, String userName, int PID){
        return filesManagerMapper.getIdByName(fileName, userName,PID);
    }

    //BF局部搜索
    public List<Files> getFilesByBFLocal(String bfValue, String userName, String suffixName){
        return filesManagerMapper.getFilesByBFLocal(bfValue, userName, suffixName);
    }

    //BF全局搜索
    public List<Files> getFilesByBFGlobal(String bfValue, String suffixName){
        return filesManagerMapper.getFilesByBFGlobal(bfValue, suffixName);
    }

    //获取到当前id的最大值，方便上传文件后将其插入数据库
    public int getMaxId(){
        return filesManagerMapper.getMaxId();
    }

    public int getBFencPID(String username){return filesManagerMapper.getBFencPID(username);}

    public int getBFdecPID(String username){return filesManagerMapper.getBFdecPID(username);}
}
