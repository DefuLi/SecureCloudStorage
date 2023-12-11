package cn.xjtu.iotlab.dao;

import cn.xjtu.iotlab.vo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper {
    int verifyPasswd(String userName, String password);

    int isApprove(String userName);

    User getUserByToken(String token);

}
