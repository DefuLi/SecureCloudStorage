package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.LoginService;
import cn.xjtu.iotlab.service.impl.FilesManagerServiceImpl;
import cn.xjtu.iotlab.service.impl.LoginServiceImpl;
import cn.xjtu.iotlab.threads.MessageThreads;
import cn.xjtu.iotlab.utils.ExcelEncDecUtil;
import cn.xjtu.iotlab.vo.Files;
import cn.xjtu.iotlab.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RestController
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private MessageThreads messageThreads;

    //判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest req, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
//        System.out.println(userName + "," + password);

        boolean res = loginService.verifyPasswd(userName, password);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功");
            jsonObject.put("token", userName);
            session.setAttribute("userName", userName);
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
        }

        Runnable runnable = messageThreads.createTask(userName);
        messageThreads.executeTask(runnable, userName);
        return jsonObject;
    }

    //判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/get_info", method = RequestMethod.GET)
    public Object loginGetInfo(HttpServletRequest req, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String token = req.getParameter("token");
        User user = loginService.getUserByToken(token);
        List<String> access = new ArrayList<>();
        access.add(token);
        user.setAccess(access);
        return user;
    }

    //判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object logOut(HttpServletRequest req, HttpSession session){

        return null;
    }

    //判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/save_error_logger", method = RequestMethod.POST)
    public Object saveErrorLogger(HttpServletRequest req, HttpSession session){

        return "success";
    }




}
