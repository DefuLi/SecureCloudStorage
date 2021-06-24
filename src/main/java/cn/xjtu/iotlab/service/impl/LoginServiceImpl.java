package cn.xjtu.iotlab.service.impl;

import cn.xjtu.iotlab.dao.LoginMapper;
import cn.xjtu.iotlab.service.LoginService;
import cn.xjtu.iotlab.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public boolean verifyPasswd(String userName, String password) {
        int approveFlag = loginMapper.isApprove(userName);
        if (approveFlag == 1) {
            return loginMapper.verifyPasswd(userName, password) > 0 ? true : false;
        } else {
            return false;
        }
    }

    @Override
    public User getUserByToken(String token) {
        return loginMapper.getUserByToken(token);
    }
}
