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
    private Integer id;
    // 用户名
    private String name;
    // 上传文件的次数
    private Integer uploadCount;
    // 申请证书的次数
    private Integer applyCertCount;
    // 文件占用磁盘总空间，单位为MB
    private float fileSize;
    // 上次计算所有行为的时间
    private Date lastTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUploadCount() {
        return uploadCount;
    }

    public void setUploadCount(int uploadCount) {
        this.uploadCount = uploadCount;
    }

    public int getApplyCertCount() {
        return applyCertCount;
    }

    public void setApplyCertCount(int applyCertCount) {
        this.applyCertCount = applyCertCount;
    }

    public float getFileSize() {
        return fileSize;
    }

    public void setFileSize(float fileSize) {
        this.fileSize = fileSize;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "Behavior{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uploadCount=" + uploadCount +
                ", applyCertCount=" + applyCertCount +
                ", fileSize=" + fileSize +
                ", lastTime=" + lastTime +
                '}';
    }
}
