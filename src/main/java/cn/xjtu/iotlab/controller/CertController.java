package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.CertService;
import cn.xjtu.iotlab.service.impl.CertServiceImpl;
import cn.xjtu.iotlab.vo.Cert;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("cert")
public class CertController {

    @Autowired
    private CertServiceImpl CertService;

    //生成证书
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object certProduce(HttpServletRequest req, HttpSession session){
        JSONObject jsonObject = new JSONObject();

        String authorizedUser = req.getParameter("authoruser");
        String authorUser = req.getParameter("myname");
        String accesstype =req.getParameter("accesstype").trim();
        String sp=authorUser+"&"+authorizedUser+"&"+accesstype;
        Cert cert = new Cert();
        cert.setCert(sp);
        cert.setAuthorizeduser(authorizedUser);
        cert.setAuthoruser(authorUser);
        cert.setAccesstype(accesstype);
        //cert.setId(5);
        boolean res = CertService.addCert(cert);
        if(res){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "证书生成成功");
            jsonObject.put("cert", sp);
            return jsonObject;
        }else{
            jsonObject.put("code", 0);
            jsonObject.put("msg", "证书生成失败");
            return jsonObject;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public Object certList(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String authorUser = req.getParameter("authoruser");
        return CertService.certList(authorUser);
    }
}
