package com.icedevcloud.common.core.api;

import com.icedevcloud.common.core.exception.ApiException;

/**
 * REST API 业务断言
 * <p>参考：org.junit.Assert</p>
 *
 * @see <p>源码</p> com.baomidou.mybatisplus.extension.api.Assert
 */
public class Assert {

    /**
     * Protect constructor since it is a static only class
     */
    protected Assert() {
    }

    /**
     * 失败结果
     *
     * @param errorCode 异常错误码
     */
    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

    /**
     * 失败断言
     *
     * @param condition
     * @param errorCode 异常错误码
     */
    public static void fail(boolean condition, IErrorCode errorCode) {
        if (condition) {
            fail(errorCode);
        }
    }

    /**
     * 失败结果
     *
     * @param message 异常错误信息
     */
    public static void fail(String message) {
        throw new ApiException(message);
    }

    /**
     * 失败断言
     *
     * @param condition
     * @param message 异常错误信息
     */
    public static void fail(boolean condition, String message) {
        if (condition) {
            fail(message);
        }
    }


}
