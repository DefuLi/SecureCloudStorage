package cn.xjtu.iotlab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("cert")
public class CertAuthorization {
    //生成证书
    @ResponseBody
    @RequestMapping(value = "/generateCert", method = RequestMethod.POST)
    public Object generateCent(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //删除证书
    @ResponseBody
    @RequestMapping(value = "/deleteCert", method = RequestMethod.POST)
    public Object deleteCent(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //申请证书
    @ResponseBody
    @RequestMapping(value = "/applyCert", method = RequestMethod.POST)
    public Object applyCent(HttpServletRequest req, HttpSession session){

        return "success";
    }
}
