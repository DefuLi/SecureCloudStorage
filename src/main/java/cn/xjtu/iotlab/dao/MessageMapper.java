package cn.xjtu.iotlab.dao;

import cn.xjtu.iotlab.vo.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息模块Dao层
 *
 * @author Defu Li
 * @date 2021/6/22 8:49
 */
@Repository
public interface MessageMapper {
    List<Message> loopUnReadMessage(String authorUser);

    List<Message> loopReadedMessage(String authorUser);

    List<Message> loopTrashList(String authorUser);
}
