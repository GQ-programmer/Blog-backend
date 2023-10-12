package zgq.cool.blogbackend.exception;

import zgq.cool.blogbackend.common.ErrorCode;

/**
 * @Author 孑然
 * @Date 2022 12/04 12:51
 */
public class BusinessException extends RuntimeException{

    private final int code;

    private final String description;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
