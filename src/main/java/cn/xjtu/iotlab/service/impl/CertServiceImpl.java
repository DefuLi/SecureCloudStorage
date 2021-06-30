package cn.xjtu.iotlab.service.impl;

import cn.xjtu.iotlab.dao.CertMapper;
import cn.xjtu.iotlab.service.CertService;
import cn.xjtu.iotlab.vo.Cert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertServiceImpl implements CertService {

    @Autowired
    private CertMapper CertMapper;

    @Override
    public boolean addCert(Cert cert){
        return CertMapper.addCert(cert)>0?true:false;
    }

    @Override
    public List<Cert> certList(String authorUser)
    {
        return CertMapper.certList(authorUser);
    }

    @Override
    public boolean deleteCert(Cert cert){return CertMapper.deleteCert(cert)>0?true:false;}

    @Override
    public boolean findCert(String authorizeduser,String authoruser){

        System.out.println("cert----------"+authoruser+authorizeduser+CertMapper.findCert(authoruser,authorizeduser));
        return CertMapper.findCert(authoruser,authorizeduser)>0?true:false;}
}
