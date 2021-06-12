package cn.xjtu.iotlab.dao;

import cn.xjtu.iotlab.vo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper {
    public int verifyPasswd(String userName, String password);

    public User getUserByToken(String token);

}
