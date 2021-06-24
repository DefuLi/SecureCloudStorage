package cn.xjtu.iotlab.service.impl;

import cn.xjtu.iotlab.dao.RegisterMapper;
import cn.xjtu.iotlab.service.RegisterService;
import cn.xjtu.iotlab.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户注册Service实现类
 *
 * @author Defu Li
 * @date 2021/6/23 20:53
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RegisterMapper registerMapper;

    @Override
    public void register(User user) {
        registerMapper.register(user);
    }
}
