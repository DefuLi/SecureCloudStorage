package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public Object approve(HttpServletRequest req, HttpSession session) {
        String name = req.getParameter("name");
        try {
            managerService.approve(name);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
