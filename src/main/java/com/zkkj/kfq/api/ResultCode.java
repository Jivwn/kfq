package com.zkkj.kfq.api;

/**
 * @description:
 * @author: Jdz
 * @create: 2020/6/23 16:47
 * @Copyright:
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),

    /* 导航页 */
    VOTE_SUCCESS(10001, "人气功能操作成功"),
    VOTE_FAIL(10002, "人气功能操作失败"),
    PARAMETER_ERROR(10003, "条件缺失"),

    /* 登录验证 */
    NOT_LOGIN(10101, "未登录"),
    VERI_CODE_ERROR(10102, "参数异常,验证码发送失败"),
    WRONG_PHONE_NUMBER(10103, "请输入正确手机号"),
    WRONG_EMAIL(10104, "请输入正确邮箱"),
    VERI_CODE_SUCCESS(10105, "验证码发送成功"),
    VERI_CODE_FAIL(10106, "验证码发送失败"),
    ACCOUNT_NOT_BOUND(10107, "未绑定账号"),

    ;

    private long code;
    private String message;
    private ResultCode(long code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
