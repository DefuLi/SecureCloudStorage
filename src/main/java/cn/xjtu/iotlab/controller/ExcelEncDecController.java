package cn.xjtu.iotlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("excel")
public class ExcelEncDecController {
    //属性加密
    @RequestMapping(value = "propertyEncrypt")
    public List<Integer> propertyEncrypt(MultipartFile file, HttpServletRequest req, HttpSession session) throws FileNotFoundException {
        String arrselect = req.getParameter("arrselect");
        //只针对Excel文件
        FileInputStream finput = new FileInputStream((File) file);

        List<Integer> results = new ArrayList<>();

        return results;
    }

    //属性解密
    @RequestMapping(value = "propertyDecrypt", method = RequestMethod.POST)
    public Object propertyDecrypt(HttpServletRequest req, HttpSession session){

        return "success";
    }




}
