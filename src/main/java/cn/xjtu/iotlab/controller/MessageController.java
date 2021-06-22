package cn.xjtu.iotlab.controller;

import cn.xjtu.iotlab.constant.MessageConstants;
import cn.xjtu.iotlab.service.MessageService;
import cn.xjtu.iotlab.vo.Message;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 消息模块Controller层
 *
 * @author Defu Li
 * @date 2021/6/22 20:31
 */
@Controller
@ResponseBody
@RequestMapping("message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public Object init(HttpServletRequest req, HttpSession session){
        String authorUser = req.getParameter("authorUser");
        List<Message> unReadList = MessageConstants.unReadMap.get(authorUser);
        List<Message> readedList = MessageConstants.readedMap.get(authorUser);
        List<Message> trashList = MessageConstants.trashMap.get(authorUser);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("unReadList", unReadList);
        jsonObject.put("readedList", readedList);
        jsonObject.put("trashList", trashList);

        return jsonObject;
    }

}
