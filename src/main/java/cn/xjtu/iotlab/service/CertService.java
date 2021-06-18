package cn.xjtu.iotlab.service;

import cn.xjtu.iotlab.vo.Cert;

import java.util.List;

public interface CertService {
    public boolean addCert(Cert cert);
    List<Cert> certList(String authorUser);
    boolean deleteCert(Cert cert);
}
