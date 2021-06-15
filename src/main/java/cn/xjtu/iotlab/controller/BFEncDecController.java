package cn.xjtu.iotlab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * BF 文件加解密模块
  */
@RequestMapping("BF")
public class BFEncDecController {
    //文件加密
    @ResponseBody
    @RequestMapping(value = "/BFEncrypt", method = RequestMethod.POST)
    public Object BFEncrypt(HttpServletRequest req, HttpSession session){

        //前端
        //保存
        //上传
        //取消
        return "success";
    }

    //文件解密
    @ResponseBody
    @RequestMapping(value = "/BFDecrypt", method = RequestMethod.POST)
    public Object BFDecrypt(HttpServletRequest req, HttpSession session){


        //前端
        //保存
        //取消

        return "success";
    }

    //文件上传
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //文件下载
    @ResponseBody
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public Object download(HttpServletRequest req, HttpSession session){

        return "success";
    }


}
