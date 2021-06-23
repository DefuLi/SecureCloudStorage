package cn.xjtu.iotlab.vo;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息实体
 *
 * @author Defu Li
 * @date 2021/6/22 12:59
 */
@Data
public class Message {
    private int msg_id;
    // 授权者
    private String authorUser;
    // 被授权者
    private String authorizedUser;
    // 0为不具备任何权限，1为具备读权限，2为具备读写权限
    private int readWrite;
    // 0为未读消息，1为已读消息，2为回收站消息
    private int messageRead;
    // 消息创建时间
    private String createTime;
    // 消息标题
    private String title;

    public int getId() {
        return msg_id;
    }

    public void setId(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(String authorUser) {
        this.authorUser = authorUser;
    }

    public String getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(String authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public int getReadWrite() {
        return readWrite;
    }

    public void setReadWrite(int readWrite) {
        this.readWrite = readWrite;
    }

    public int getMessageRead() {
        return messageRead;
    }

    public void setMessageRead(int messageRead) {
        this.messageRead = messageRead;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        SimpleDateFormat start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = start.format(createTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
