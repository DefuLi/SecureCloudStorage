package cn.xjtu.iotlab.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Files {
    @JsonProperty("Id")
    private int Id;//文件id

    @JsonProperty("ParentId")
    private int ParentId;//父目录id
    @JsonProperty("Name")
    private String Name;//文件名
    @JsonProperty("Describe")
    private String Describe;//描述信息
    @JsonProperty("CreateUserName")
    private String CreateUserName;//创建用户名称
    @JsonProperty("CreateTime")
    private Date CreateTime;//创建时间
    @JsonProperty("EditTime")
    private Date EditTime;//上次编辑时间
    @JsonProperty("EditBy")
    private String EditBy;//上次编辑用户
    @JsonProperty("Type")
    private int Type;//文件图标类型，1是文件夹
    @JsonProperty("FileType")
    private int FileType;//文件类型，0是文件夹
    @JsonProperty("Size")
    private Integer Size;//文件大小
    @JsonProperty("SuffixName")
    private String SuffixName;//后缀名

    @JsonProperty("Parents")
    private String Parents;
    @JsonProperty("RourceId")
    private String RourceId;
    @JsonProperty("WBSId")
    private String WBSId;
    @JsonProperty("RourceType")
    private int RourceType;
    @JsonProperty("ISAllVisible")
    private int ISAllVisible;
    @JsonProperty("PictuerCount")
    private int PictuerCount;
    @JsonProperty("FileCount")
    private int FilesCount;
    @JsonProperty("FileId")
    private String FileId;
    @JsonProperty("PermissionType")
    private int PermissionType;
    @JsonProperty("ProjectId")
    private String ProjectId;
    @JsonProperty("IdentityId")
    private int IdentityId;
}
