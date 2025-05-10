package com.inSoft.pojo.result;

import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.List;

/**
 * layui可用的分页返回信息
 */
public class PageResult {
    private Integer code; // 1 成功, 0 失败
    private String msg; // 提示信息
    private Long count; // 总条数
    private List<?> data; // 数据 data

    public PageResult() {
    }

    public PageResult(Integer code, String msg, Long count, List<?> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public static PageResult success(String msg, PageInfo<?> pageInfo) {
        return new PageResult(0, msg, pageInfo.getTotal(), pageInfo.getList());
    }

    public static PageResult success(String msg, List<?> pageList) {
        if (pageList instanceof Collection) {
            long total = ((Collection<?>) pageList).size();
            return new PageResult(0, msg, total, pageList);
        } else {
            throw new IllegalArgumentException("Data must be a collection type to calculate total size.");
        }
    }

    public static PageResult success(String msg) {
        return new PageResult(0, msg, null, null);
    }

    public static PageResult error(String msg) {
        return new PageResult(1, msg, null, null);
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}