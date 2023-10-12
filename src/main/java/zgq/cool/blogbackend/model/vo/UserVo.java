package zgq.cool.blogbackend.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author 孑然
 * @Date 2022 12/13 18:23
 */
@Data
public class UserVo {

    /**
     * 用户id
     */
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

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户的所有文章列表
     */
    private List<ArticleVo> articleVoList;

    private static final long serialVersionUID = 1L;

}
