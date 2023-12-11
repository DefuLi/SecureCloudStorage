package cn.xjtu.iotlab.dao;

import cn.xjtu.iotlab.vo.Behavior;
import cn.xjtu.iotlab.vo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Defu Li
 * @date 2021/6/23 21:43
 */
@Repository
public interface ManagerMapper {
    void approve(String name);

    List<User> getRegister();

    Behavior userBehavior();
}
