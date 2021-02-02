package com.zkkj.kfq.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 共同的结果
 * 共通返回集
 *
 * @author 13745
 * @date 2020/12/29
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult<T> {
    /**
     * 状态码
     */
    private long code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 返回体
     */
    private T data;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
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

    public CommonResult() {
    }

    public CommonResult(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> CommonResult<T> fail(String message) {
        return new CommonResult<>(ResultCode.FAIL.getCode(), message);
    }

    public static <T> CommonResult<T> fail(long code, String message) {
        return new CommonResult<>(code, message);
    }

}
