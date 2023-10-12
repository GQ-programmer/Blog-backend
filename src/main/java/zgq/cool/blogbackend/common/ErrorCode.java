package zgq.cool.blogbackend.common;

/**
 * 自定义错误码
 *
 * @author 孑然
 */
public enum ErrorCode {

    /**
     * 成功
     */
    SUCCCESS(0, "ok", "请求成功"),

    PARAMS_ERROR(40000,"请求参数错误","请求参数错误"),

    NO_LOGIN(40100,"未登录","未登录"),

    NO_AUTH(40101,"无权限","无权限"),

    SYSTEM_ERROR(50000,"系统内部异常","系统内部异常"),

    UA_NOT_MATCH_PW(40002,"用户名与密码不匹配","用户名与密码不匹配");

    private final int code;


    private final String message;


    private final String description;


    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
