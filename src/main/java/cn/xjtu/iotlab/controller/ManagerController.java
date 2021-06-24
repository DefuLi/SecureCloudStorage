package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.ManagerService;
import cn.xjtu.iotlab.vo.Behavior;
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
    @RequestMapping(value = "/getRegisters", method = RequestMethod.POST)
    public Object getRegister(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tableData", managerService.getRegister());
        return jsonObject;
    }

    /**
     * 获取近7天的用户行为
     *
     * @param req 请求
     * @return json对象
     */
    @RequestMapping(value = "/userBehavior", method = RequestMethod.POST)
    public Object userBehavior(HttpServletRequest req) {
        Behavior behavior = managerService.userBehavior();
        float score = managerService.computeScore();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("behavior", behavior);
        jsonObject.put("score", score);
        return jsonObject;
    }
}
