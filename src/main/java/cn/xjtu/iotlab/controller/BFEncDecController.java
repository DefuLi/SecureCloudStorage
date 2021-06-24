package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.utils.encdec.TestBF;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * BF 文件加解密模块
  */
@RestController
@RequestMapping("bf")
public class BFEncDecController {
    //文件加密
    @ResponseBody
    @RequestMapping(value = "/bfencrypt", method = RequestMethod.POST)
    public int BFEncrypt(@RequestParam("file") MultipartFile multipartFile , @RequestParam("username") String username) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        TestBF bf = new TestBF();
        System.out.println(username);
        boolean signal= bf.filebfencrpt(file,username);
        //前端
        //保存
        //上传
        //取消
        if(signal == true)
        return 1;
        else{
            return 0;
        }
    }

    //文件解密
    @ResponseBody
    @RequestMapping(value = "/bfdecrypt", method = RequestMethod.POST)
    public int BFDecrypt(@RequestParam("file") MultipartFile multipartFile , @RequestParam("username") String username) throws IOException, BadPaddingException, IllegalBlockSizeException {
        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        TestBF bf = new TestBF();
        System.out.println(username);
        boolean signal= bf.filebfdecrpt(file,username);
        //前端
        //保存
        //取消
        if(signal == true)
            return 1;
        else{
            return 0;
        }
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
