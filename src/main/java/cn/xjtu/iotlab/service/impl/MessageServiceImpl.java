package cn.xjtu.iotlab.service.impl;

import cn.xjtu.iotlab.dao.MessageMapper;
import cn.xjtu.iotlab.service.MessageService;
import cn.xjtu.iotlab.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息模块Service实现类
 *
 * @author Defu Li
 * @date 2021/6/22 14:00
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<Message> loopUnReadMessage(String authorUser) {
        return messageMapper.loopUnReadMessage(authorUser);
    }

    @Override
    public List<Message> loopReadedMessage(String authorUser) {
        return messageMapper.loopReadedMessage(authorUser);
    }

    @Override
    public List<Message> loopTrashMessage(String authorUser) {
        return messageMapper.loopTrashMessage(authorUser);
    }

    @Override
    public void apply(Message message) {
        messageMapper.apply(message);
    }

    @Override
    public void hasRead(int id) {
        messageMapper.hasRead(id);
    }

    @Override
    public void removeReaded(int id) {
        messageMapper.removeReaded(id);
    }

    @Override
    public void restoreTrash(int id) {
        messageMapper.restoreTrash(id);
    }
}
