package com.inSoft.pojo.result;

/**
 * 同意文件上传返回参数
 */
public class FileResult {
    private Integer code ;//0 成功 , 1 失败
    private String msg; //提示信息
    private Object data; //数据 data

    public FileResult() {
    }
    public FileResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static FileResult success(String msg, Object data){
        return new FileResult(0, msg, data);
    }
    public static FileResult success(String msg){
        return new FileResult(0, msg, null);
    }
    public static FileResult error(String msg){
        return new FileResult(1, msg, null);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
