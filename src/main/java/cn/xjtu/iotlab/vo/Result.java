package cn.xjtu.iotlab.vo;

import java.util.Arrays;

public class Result {
    private static final int SUCCESS = 200;
    private static final int FAILED = 500;
    private int code;
    private String[] tableHead;
    private String[] tableData;

    public Result(int code, String[] tableHead, String[] tableData) {
        this.code = code;
        this.tableHead = tableHead;
        this.tableData = tableData;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", tableHead=" + Arrays.toString(tableHead) +
                ", tableData=" + Arrays.toString(tableData) +
                '}';
    }
}
