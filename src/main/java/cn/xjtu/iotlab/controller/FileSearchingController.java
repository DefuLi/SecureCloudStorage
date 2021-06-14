package cn.xjtu.iotlab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("searching")
public class FileSearchingController {
    //BF参数加密
    @ResponseBody
    @RequestMapping(value = "/BFParameterEnc", method = RequestMethod.POST)
    public Object BFParameterEnc(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //明文检索
    @ResponseBody
    @RequestMapping(value = "/searching", method = RequestMethod.POST)
    public Object searching(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //密文检索
    @ResponseBody
    @RequestMapping(value = "/BFSearching", method = RequestMethod.POST)
    public Object BFSearching(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //检索下载
    @ResponseBody
    @RequestMapping(value = "/searchingDownload", method = RequestMethod.POST)
    public Object searchingDownload(HttpServletRequest req, HttpSession session){

        //明文：下载
        //密文：解密-》下载
        return "success";
    }

    //Excel运算
    @ResponseBody
    @RequestMapping(value = "/excelOperating", method = RequestMethod.POST)
    public Object excelOperating(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //全局部分

    //全局明文检索
    @ResponseBody
    @RequestMapping(value = "/searchingAll", method = RequestMethod.POST)
    public Object searchingAll(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //全局密文检索
    @ResponseBody
    @RequestMapping(value = "/BFSearchingAll", method = RequestMethod.POST)
    public Object BFSearchingAll(HttpServletRequest req, HttpSession session){

        return "success";
    }
}
