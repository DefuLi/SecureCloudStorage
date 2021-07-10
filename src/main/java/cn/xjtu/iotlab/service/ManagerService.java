package cn.xjtu.iotlab.service;

import cn.xjtu.iotlab.vo.Behavior;
import cn.xjtu.iotlab.vo.User;

import java.util.List;

/**
 * 管理员Service层
 *
 * @author Defu Li
 * @date 2021/6/23 21:42
 */
public interface ManagerService {
    void approve(String name);

    List<User> getRegister();

    List<Behavior> userBehavior();

    Float computeScoreById(int id);
}
