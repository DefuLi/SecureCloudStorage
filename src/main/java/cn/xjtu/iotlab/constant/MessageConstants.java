package cn.xjtu.iotlab.constant;

import cn.xjtu.iotlab.vo.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息模块常量
 *
 * @author Defu Li
 * @date 2021/6/22 12:57
 */
public class MessageConstants {
    public static Map<String, List<Message>> unReadMap = new HashMap<>();
    public static Map<String, List<Message>> readedMap = new HashMap<>();
    public static Map<String, List<Message>> trashMap = new HashMap<>();
}
