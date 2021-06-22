package cn.xjtu.iotlab.service;

import cn.xjtu.iotlab.vo.Files;

import java.util.List;
import java.util.Set;

public interface FilesManagerService {

    public List<Files> getAllFileList(String userName);

    //向数据库中插入文件
    public int insertFiles(Files file);

    //根据id搜索文件
    public Files searchById(int id);

    //获取数据库中所有id
    public Set<Integer> getAllId();
}