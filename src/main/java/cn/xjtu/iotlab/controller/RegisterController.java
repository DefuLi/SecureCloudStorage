package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.RegisterService;
import cn.xjtu.iotlab.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jnlp.UnavailableServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户注册Controller层
 *
 * @author Defu Li
 * @date 2021/6/23 20:51
 */
@Controller
@ResponseBody
@RequestMapping("register")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Object register(HttpServletRequest req, HttpSession session) {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setApproveFlag(0);
        List<String> access = new ArrayList<>();
        access.add(name);
        user.setAccess(access);

        try {
            registerService.register(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
