package cn.xjtu.iotlab.service.impl;

import cn.xjtu.iotlab.dao.FilesManagerMapper;
import cn.xjtu.iotlab.service.FilesManagerService;
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
}
