package cn.xjtu.iotlab.constant;

import cn.xjtu.iotlab.vo.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息模块常量
 *
 * @author Defu Li
 * @date 2021/6/22 12:57
 */
public class MessageConstants {
    public static List<Message> unReadList = new ArrayList<>();
    public static List<Message> readedList = new ArrayList<>();
    public static List<Message> trashList = new ArrayList<>();
}
