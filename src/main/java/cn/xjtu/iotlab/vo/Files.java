package cn.xjtu.iotlab.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Files {
    private int Id;//文件id
    private int ParentId;//父目录id
    private String Name;//文件名
    private String Describe;//描述信息
    private String CreateUserName;//创建用户名称
    private Date CreateTime;//创建时间
    private Date EditTime;//上次编辑时间
    private String EditBy;//上次编辑用户
    private int Type;//文件图标类型，1是文件夹
    private int FileType;//文件类型，0是文件夹
    private Integer Size;//文件大小
    private String SuffixName;//后缀名

//    private String Parents;
//    private String RourceId;
//    private String WBSId;
//    private int RourceType;
//    private int ISAllVisible;
//    private int PictuerCount;
//    private int FilesCount;
//    private String FileId;
//    private int PermissionType;
//    private String ProjectId;
//    private int IdentityId;
}
