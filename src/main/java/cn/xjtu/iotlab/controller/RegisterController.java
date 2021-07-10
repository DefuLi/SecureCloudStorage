package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.RegisterService;
import cn.xjtu.iotlab.utils.encdec.Hash;
import cn.xjtu.iotlab.vo.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jnlp.UnavailableServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String name = req.getParameter("userName");
        String password = req.getParameter("password");
        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_date = start.format(date);//结束时间

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setApproveFlag(0);
        List<String> access = new ArrayList<>();
        access.add(name);
        user.setAccess(access);
        user.setApplyDate(create_date);
        try {
            registerService.register(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
