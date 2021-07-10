package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.ManagerService;
import cn.xjtu.iotlab.vo.Behavior;
import cn.xjtu.iotlab.vo.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 管理员Controller层，用于审批用户注册的账号，以及监控用户的行为
 *
 * @author Defu Li
 * @date 2021/6/23 21:39
 */
@Controller
@ResponseBody
@RequestMapping("manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    /**
     * 审批用户的注册
     *
     * @param req 请求
     * @return json对象
     */
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public Object approve(HttpServletRequest req) {
        String name = req.getParameter("name");
        try {
            managerService.approve(name);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取未经审批的用户
     *
     * @param req 请求
     * @return json对象
     */
    @RequestMapping(value = "/getRegisters", method = RequestMethod.GET)
    public void getRegister(HttpServletRequest req,HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        List<LinkedHashMap> lists = new ArrayList<>();
        List<User> userList = managerService.getRegister();
        for (User user: userList) {
            LinkedHashMap<String,String> hashMap = new LinkedHashMap<>();
            hashMap.put("userName",user.getName());
            hashMap.put("id",user.getId().toString());
            hashMap.put("status","未审核");
            Date date = new Date(); // this object contains the current date value
            SimpleDateFormat start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create_date = start.format(date);//结束时间
            hashMap.put("createTime",create_date);
            lists.add(hashMap);
        }
        jsonObject.put("tableData",lists);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    /**
     * 获取近7天的用户行为
     *
     * @param req 请求
     * @return json对象
     */
    @RequestMapping(value = "/userBehavior", method = RequestMethod.GET)
    public void userBehavior(HttpServletRequest req,HttpServletResponse response) throws IOException {
        List<Behavior> behaviorList = managerService.userBehavior();
        JSONObject jsonObject = new JSONObject();
        List<LinkedHashMap> lists = new ArrayList<>();
        for(Behavior behavior : behaviorList){
            LinkedHashMap<String,String> hashMap = new LinkedHashMap<>();
            hashMap.put("userName",behavior.getName());
            hashMap.put("score",managerService.computeScoreById(behavior.getId()).toString());
            hashMap.put("uploadCount",((Integer)behavior.getUploadCount()).toString());
            hashMap.put("fileSize",((Float)behavior.getFileSize()).toString());
            hashMap.put("applyCertCount",((Integer)behavior.getApplyCertCount()).toString());
            lists.add(hashMap);
        }
        jsonObject.put("tableData",lists);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }
}
