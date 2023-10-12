package zgq.cool.blogbackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 孑然
 * @Date 2022 12/04 12:12
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private String message;

    private String description;

    private T data;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code,T data, String message) {
        this(code,data, message, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null, errorCode.getMessage(), errorCode.getDescription());
    }

}
