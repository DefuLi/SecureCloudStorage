package cn.xjtu.iotlab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("excelOperation")
public class ExcelCiphertextOperationController {
    //保序运算
    @ResponseBody
    @RequestMapping(value = "/opeOperation", method = RequestMethod.POST)
    public Object opeOperation(HttpServletRequest req, HttpSession session){

        return "success";
    }

    //算术运算
    @ResponseBody
    @RequestMapping(value = "/arithmeticOperation", method = RequestMethod.POST)
    public Object arithmeticOperation(HttpServletRequest req, HttpSession session){

        return "success";
    }
}
