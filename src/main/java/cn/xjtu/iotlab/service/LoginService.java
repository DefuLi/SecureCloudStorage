package cn.xjtu.iotlab.service;

import cn.xjtu.iotlab.vo.User;

public interface LoginService {
    public boolean verifyPasswd(String userName, String password);

    public User getUserByToken(String token);

}
