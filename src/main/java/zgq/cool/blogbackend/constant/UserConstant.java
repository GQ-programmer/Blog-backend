package zgq.cool.blogbackend.constant;

/**
 * 用户常量
 *
 * @Author 孑然
 */
public interface UserConstant {

    /**
     * 用户登录状态键（session）
     */
    String USER_LOGIN_STATE = "userLoginState";

    //  -------- 权限

    /**
     * 默认权限
     */
    int DEFAULT_ROLE =  0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE =  1;
}
