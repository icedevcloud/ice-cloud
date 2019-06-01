package com.xiaobingby.common.core.util;

import java.io.Serializable;

/**
 * R 返回包装类
 *
 * @author XiaoBingBy
 * @date 2018-08-30 16:21
 * @since 1.0
 */
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1;

    private Integer code;

    private String message;

    private T data;

    private Long timestamp;

    /**
     * 构造方法
     */
    public R() {
    }

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        R<T> tr = new R<>();
        tr.setData(data);
        tr.setCode(200);
        return tr.putTimeStamp();
    }

    public static <T> R<T> error(String msg) {
        return error(500, msg);
    }

    public static <T> R<T> error(int code, String msg) {
        R<T> tr = new R<>();
        tr.setCode(code);
        tr.setMessage(msg);
        return tr.putTimeStamp();
    }

    public static <T> R<T> error(int code, T data, String msg) {
        R<T> tr = new R<>();
        tr.setCode(code);
        tr.setData(data);
        tr.setMessage(msg);
        return tr.putTimeStamp();
    }

    /**
     * 设置时间戳
     *
     * @return
     */
    private R<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }

}
