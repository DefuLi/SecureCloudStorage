package cn.xjtu.iotlab.service;

import cn.xjtu.iotlab.vo.Message;

import java.util.List;

/**
 * 消息模块Service层
 *
 * @author Defu Li
 * @date 2021/6/22 13:58
 */
public interface MessageService {
    List<Message> loopUnReadMessage(String authorUser);
    List<Message> loopReadedMessage(String authorUser);
    List<Message> loopTrashMessage(String authorUser);
}
