package cn.xjtu.iotlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Defu Li
 * @date 2021/6/10 17:08
 */
@Controller
@ResponseBody
@CrossOrigin
public class UserController {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public int handleUser() {
        return 123456;
    }

    @RequestMapping(value = "/get_info", method = RequestMethod.GET)
    public String getUserInfo(String token) {
        Map<String, String> superAdmin = new HashMap<>();
        superAdmin.put("name", "super_admin");
        superAdmin.put("user_id", "1");
        superAdmin.put("access", )
        return token;
    }
}

