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
import java.util.Date;
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
    public Object init(HttpServletRequest req, HttpSession session) {
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

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Object apply(HttpServletRequest req, HttpSession session) {
        String authorUser = req.getParameter("authorUser");
        String authorizedUser = req.getParameter("authorizedUser");
        int readWrite = Integer.parseInt(req.getParameter("readWrite"));
        String title = req.getParameter("title");

        int messageRead = 0;
        Date createTime = new Date();
        Message message = new Message();
        message.setAuthorUser(authorUser);
        message.setAuthorizedUser(authorizedUser);
        message.setReadWrite(readWrite);
        message.setMessageRead(messageRead);
        message.setCreateTime(createTime);
        message.setTitle(title);

        try {
            messageService.apply(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/hasRead", method = RequestMethod.POST)
    public Object hasRead(HttpServletRequest req, HttpSession session) {
        int id = Integer.parseInt(req.getParameter("msg_id"));
        try {
            messageService.hasRead(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/removeReaded", method = RequestMethod.POST)
    public Object removeReaded(HttpServletRequest req, HttpSession session) {
        int id = Integer.parseInt(req.getParameter("msg_id"));
        try {
            messageService.removeReaded(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/restoreTrash", method = RequestMethod.POST)
    public Object restoreTrash(HttpServletRequest req, HttpSession session) {
        int id = Integer.parseInt(req.getParameter("msg_id"));
        try {
            messageService.restoreTrash(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
