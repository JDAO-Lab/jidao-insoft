package com.inSoft.pojo.result;

import lombok.Data;

public class ResultTree {
    /**
     * 状态信息
     */
    private Status status;

    /**
     * 返回数据
     */
    private Object data;

    public ResultTree() {
    }

    public ResultTree(Object data) {
        this.status = new Status();
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultTree success(Object data) {
        return new ResultTree(data);
    }

    /**
     * 所需内部类
     */
    @Data
    public class Status {

        private Integer code = 200;

        private String message = "默认";

    }

    @Override
    public String toString() {
        return "ResultTree{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
