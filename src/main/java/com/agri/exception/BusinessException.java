package com.agri.exception;

import lombok.Getter;

/**
 * 自定义业务异常
 * <p>
 * 业务校验不通过（如：要编辑的品种不存在）时抛出，
 * 由全局异常处理器统一捕获并转换为友好 JSON。
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = 400;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
