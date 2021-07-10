package cn.xjtu.iotlab.service.impl;

import cn.xjtu.iotlab.dao.ManagerMapper;
import cn.xjtu.iotlab.service.ManagerService;
import cn.xjtu.iotlab.vo.Behavior;
import cn.xjtu.iotlab.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<User> getRegister() {
        return managerMapper.getRegister();
    }

    @Override
    public List<Behavior> userBehavior() {
        List<Behavior> behaviorList = managerMapper.userBehavior();
        // TODO 需要有一个中转程序，收集各模块的行为，
        //  判断lastTime是否为近7天，否则更新数据。
        return behaviorList;
    }

    @Override
    public Float computeScoreById(int id) {
        // TODO 待实现
        return 0f;
    }
}
