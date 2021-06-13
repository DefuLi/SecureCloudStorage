package cn.xjtu.iotlab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//监测用户行为
public class AccessController {

    //展示用户行为
    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public Object getUserInfo(HttpServletRequest req, HttpSession session){

        return null;
    }
}
