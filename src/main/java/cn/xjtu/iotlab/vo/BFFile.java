package cn.xjtu.iotlab.vo;

import lombok.Data;

@Data
public class BFFile {

    private int id;//文件的BF值
    private String name;//加密文件名
    private String BFValue;//BF值

    public BFFile(){}

    public BFFile(int id, String name, String BFValue){
        this.BFValue = BFValue;
        this.id = id;
        this.name = name;
    }

}
