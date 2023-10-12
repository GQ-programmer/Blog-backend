package zgq.cool.blogbackend.model.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * @Author 孑然
 * @Date 2022 12/12 18:18
 */
@Data
public class UserUpdateRequest {

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户简介
     */
    private String description;

    /**
     * 用户头像地址
     */
    private String avatarUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地区
     */
    private String area;

    /**
     * 出生日期（年龄）
     */
    private String birthday;

    /**
     * 0-男  1-女
     */
    private Character gender;


    private static final long serialVersionUID = 1L;
}
