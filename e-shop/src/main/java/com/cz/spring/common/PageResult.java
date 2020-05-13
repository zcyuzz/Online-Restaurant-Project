package com.cz.spring.common;

import java.util.List;


public class PageResult<T> {

    private int code; // 0 = success

    private String msg;  

    private long count; 

    private List<T> data; 

    public PageResult() {
    }

    public PageResult(List<T> rows) {
        this(rows, rows.size());
    }

    public PageResult(List<T> rows, long total) {
        this.count = total;
        this.data = rows;
        this.code = 0;
        this.msg = "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
