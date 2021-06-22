package cn.xjtu.iotlab.vo;

import lombok.Data;

import java.util.Date;

/**
 * 消息实体
 *
 * @author Defu Li
 * @date 2021/6/22 12:59
 */
@Data
public class Message {
    private int id;
    // 授权者
    private String authorUser;
    // 被授权者
    private String authorizedUser;
    // 0为不具备任何权限，1为具备读权限，2为具备读写权限
    private int readWrite;
    // 0为未读消息，1为已读消息，2为回收站消息
    private int messageRead;
    // 消息创建时间
    private Date createTime;
    // 消息标题
    private String title;
}
