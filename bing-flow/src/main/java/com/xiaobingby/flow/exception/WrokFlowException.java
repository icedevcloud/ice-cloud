package com.xiaobingby.flow.exception;

import com.xiaobingby.common.core.api.ErrorCode;
import com.xiaobingby.common.core.api.IErrorCode;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/10/27
 * Time: 14:02
 * Describe:
 */
public class WrokFlowException extends Exception {

    /**
     * 错误码
     */
    private IErrorCode errorCode;

    public WrokFlowException(IErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public WrokFlowException(String message) {
        super(message);
        this.errorCode = new ErrorCode(500,message);
    }

    public WrokFlowException(int code,String message) {
        super(message);
        this.errorCode = new ErrorCode(code,message);
    }
}
