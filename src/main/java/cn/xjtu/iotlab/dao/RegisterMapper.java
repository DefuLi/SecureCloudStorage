package cn.xjtu.iotlab.dao;

import cn.xjtu.iotlab.vo.User;
import org.springframework.stereotype.Repository;

/**
 * 用户注册Dao层
 *
 * @author Defu Li
 * @date 2021/6/23 20:54
 */
@Repository
public interface RegisterMapper {
    void register(User user);
}
