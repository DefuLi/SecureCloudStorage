package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.service.CertService;
import cn.xjtu.iotlab.service.impl.CertServiceImpl;
import cn.xjtu.iotlab.utils.ExcelEncDecUtil;
import cn.xjtu.iotlab.utils.encdec.Cert1;
import cn.xjtu.iotlab.vo.Cert;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("cert")
public class CertController {
    private String head0 = ExcelEncDecUtil.head0;
    private long opeart_k = ExcelEncDecUtil.opeart_k;
    @Autowired
    private CertServiceImpl CertService;

    //生成证书
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object certProduce(@RequestParam("authorizeduser")String authorizeduser,@RequestParam("authoruser") String authoruser, @RequestParam("accesstype") int accesstype){
        JSONObject jsonObject = new JSONObject();
        String aes_key="123456"+authoruser;
        String rsa_key1="123456";
        String rsa_key2="123456";
        long opeart_k=25689L;
        int cescmc_k=1;
        int cescmc_n=1;
        String authorizedUser = authorizeduser;
        String authorUser = authoruser;
        Cert1 cert1 = new Cert1();
        String sp=cert1.produceCert2(authorUser,authorizedUser,aes_key,rsa_key1,rsa_key2,opeart_k,cescmc_k,cescmc_n);
        Cert cert = new Cert();
        cert.setCert(sp);
        cert.setAuthorizeduser(authorizedUser);
        cert.setAuthoruser(authorUser);
        cert.setAccesstype(accesstype);
        cert.setAeskey(aes_key);
        cert.setRsa_key1(rsa_key1);
        cert.setRsa_key2(rsa_key2);
        cert.setOpeart_k(opeart_k);
        cert.setCescmc_k(cescmc_k);
        cert.setCescmc_n(cescmc_n);
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

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteCert(@RequestParam("authorizeduser") String authorizeduser,@RequestParam("authoruser") String authoruser,@RequestParam("accesstype") int accesstype){
        JSONObject jsonObject = new JSONObject();
        Cert cert = new Cert();
        String authorizedUser = authorizeduser;
        String authorUser = authoruser;
        String aes_key="123456"+authoruser;
        String rsa_key1="123456";
        String rsa_key2="123456";
        long opeart_k=25689L;
        int cescmc_k=1;
        int cescmc_n=1;
        Cert1 cert1 = new Cert1();
        String sp=cert1.produceCert2(authorUser,authorizedUser,aes_key,rsa_key1,rsa_key2,opeart_k,cescmc_k,cescmc_n);
        cert.setAuthorizeduser(authorizedUser);
        cert.setAuthoruser(authorUser);
        cert.setAccesstype(accesstype);
        cert.setCert(sp);
        boolean res = CertService.deleteCert(cert);
        if(res){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "证书删除成功");
            return jsonObject;
        }else{
            jsonObject.put("code", 0);
            jsonObject.put("msg", "证书删除失败");
            return jsonObject;
        }
    }
}
