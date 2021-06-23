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

    @RequestMapping(value = "/getFileList", method = RequestMethod.POST)
    @ResponseBody
    public Object getFileList(HttpServletRequest req, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String userName = req.getParameter("userName");
        System.out.println(userName);
        jsonObject.put("Message", "操作成功");
        jsonObject.put("StatusCode",200);
        jsonObject.put("CallbackType", null);
        jsonObject.put("Data", filesManagerService.getRootFile("admin"));

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
        String path = "/Users/wangchenyu/iotlab/admin";
//        String userName = "admin";
        String userName = req.getParameter("userName");
        System.out.println("-------get file-------");
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Message", "操作成功");
        jsonObject.put("StatusCode",200);
        jsonObject.put("CallbackType", null);
        jsonObject.put("Data", filesManagerService.getAllFileList(userName));

        return jsonObject;
    }





}
