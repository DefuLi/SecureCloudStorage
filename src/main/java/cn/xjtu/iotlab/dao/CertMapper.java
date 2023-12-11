package cn.xjtu.iotlab.dao;

import cn.xjtu.iotlab.vo.Cert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertMapper {
    int addCert(Cert cert);
    List<Cert> certList(String authorUser);
    int deleteCert(Cert cert);
    int findCert(String authoruser, String authorizeduser);
}
