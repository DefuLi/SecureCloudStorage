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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getCreateUserName() {
        return CreateUserName;
    }

    public void setCreateUserName(String createUserName) {
        CreateUserName = createUserName;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public Date getEditTime() {
        return EditTime;
    }

    public void setEditTime(Date editTime) {
        EditTime = editTime;
    }

    public String getEditBy() {
        return EditBy;
    }

    public void setEditBy(String editBy) {
        EditBy = editBy;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getFileType() {
        return FileType;
    }

    public void setFileType(int fileType) {
        FileType = fileType;
    }

    public Integer getSize() {
        return Size;
    }

    public void setSize(Integer size) {
        Size = size;
    }

    public String getSuffixName() {
        return SuffixName;
    }

    public void setSuffixName(String suffixName) {
        SuffixName = suffixName;
    }

    public String getParents() {
        return Parents;
    }

    public void setParents(String parents) {
        Parents = parents;
    }

    public String getRourceId() {
        return RourceId;
    }

    public void setRourceId(String rourceId) {
        RourceId = rourceId;
    }

    public String getWBSId() {
        return WBSId;
    }

    public void setWBSId(String WBSId) {
        this.WBSId = WBSId;
    }

    public int getRourceType() {
        return RourceType;
    }

    public void setRourceType(int rourceType) {
        RourceType = rourceType;
    }

    public int getISAllVisible() {
        return ISAllVisible;
    }

    public void setISAllVisible(int ISAllVisible) {
        this.ISAllVisible = ISAllVisible;
    }

    public int getPictuerCount() {
        return PictuerCount;
    }

    public void setPictuerCount(int pictuerCount) {
        PictuerCount = pictuerCount;
    }

    public int getFilesCount() {
        return FilesCount;
    }

    public void setFilesCount(int filesCount) {
        FilesCount = filesCount;
    }

    public String getFileId() {
        return FileId;
    }

    public void setFileId(String fileId) {
        FileId = fileId;
    }

    public int getPermissionType() {
        return PermissionType;
    }

    public void setPermissionType(int permissionType) {
        PermissionType = permissionType;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public int getIdentityId() {
        return IdentityId;
    }

    public void setIdentityId(int identityId) {
        IdentityId = identityId;
    }
}
