package cn.xjtu.iotlab.dao;

import org.springframework.stereotype.Repository;

/**
 * @author Defu Li
 * @date 2021/6/23 21:43
 */
@Repository
public interface ManagerMapper {
    void approve(String name);
}
