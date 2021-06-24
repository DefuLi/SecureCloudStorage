package cn.xjtu.iotlab.service.impl;

import cn.xjtu.iotlab.dao.ManagerMapper;
import cn.xjtu.iotlab.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Defu Li
 * @date 2021/6/23 21:43
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerMapper managerMapper;

    @Override
    public void approve(String name) {
        managerMapper.approve(name);
    }
}
