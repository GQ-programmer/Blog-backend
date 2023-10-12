package zgq.cool.blogbackend.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 孑然
 * @Date 2022 12/04 12:42
 */
@Data
public class UserRegisterRequest implements Serializable {

    /**
     * 昵称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String checkPassword;
}
