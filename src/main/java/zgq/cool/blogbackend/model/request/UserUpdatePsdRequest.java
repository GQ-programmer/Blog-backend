package zgq.cool.blogbackend.model.request;

import lombok.Data;

/**
 * @Author 孑然
 * @Date 2022 12/13 16:36
 */
@Data
public class UserUpdatePsdRequest {

    private Long id;

    private String oldPassword;

    private String newPassword;

    private String checkPassword;
}
