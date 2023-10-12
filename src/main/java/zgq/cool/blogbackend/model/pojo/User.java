package zgq.cool.blogbackend.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 用户
 * @author jieran
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

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

    /**
     * 0-普通用户 1-管理员
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}