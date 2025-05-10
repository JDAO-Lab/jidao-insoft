package com.inSoft.pojo.result;

/**
 * 统一响应结果封装类
 */
public class Result {
    private Integer status ;//1 成功 , 0 失败
    private String message; //提示信息
    private Object data; //数据 data

    public Result() {
    }
    public Result(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success(String message,Object data){
        return new Result(200, message, data);
    }
    public static Result success(String message){
        return new Result(200, message, null);
    }
    public static Result error(String message){
        return new Result(201, message, null);
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
