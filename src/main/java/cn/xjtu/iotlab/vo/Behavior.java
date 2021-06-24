package cn.xjtu.iotlab.vo;

import java.util.Date;

/**
 * 近7天的用户行为
 *
 * @author Defu Li
 * @date 2021/6/24 22:34
 */
public class Behavior {
    // id
    private int id;
    // 用户名
    private String name;
    // 上传文件的次数
    private int uploadCount;
    // 申请证书的次数
    private int applyCertCount;
    // 文件占用磁盘总空间，单位为MB
    private float fileSize;
    // 上次计算所有行为的时间
    private Date lastTime;
}
