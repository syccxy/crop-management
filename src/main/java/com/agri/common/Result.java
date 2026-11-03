package com.agri.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一返回结果封装
 *
 * @param <T> 数据类型
 */
@Data
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 状态码：200 成功，400 参数/业务失败，500 系统异常 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 数据 */
    private T data;

    private Result() {
    }

    /** 成功（带数据） */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    /** 成功（不带数据） */
    public static <T> Result<T> success() {
        return success(null);
    }

    /** 失败：自定义状态码与提示 */
    public static <T> Result<T> fail(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /** 失败：参数 / 业务失败，固定 400 */
    public static <T> Result<T> fail(String msg) {
        return fail(400, msg);
    }
}
