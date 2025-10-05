package com.pzj.lease.common.result;

import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {

    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;

    public Result() {
    }

    //私有方法
    private static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null)
            result.setData(data);
        return result;
    }

    //公开方法，一个是要返回的数据，另一个是返回状态码，该方法用来构造返回值
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }


    //请求成功时构造返回值
    public static <T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    //当一个接口没有返回值时使用这个方法
    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    //请求失败时使用该方法
    public static <T> Result<T> fail() {
        return build(null, ResultCodeEnum.FAIL);
    }

    public static <T> Result<T> fail(Integer code,String message) {
        Result<T> result= build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
