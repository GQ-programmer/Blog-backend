package zgq.cool.blogbackend.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import zgq.cool.blogbackend.model.pojo.User;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 孑然
 * @Date 2022 12/05 18:05
 */
@Data
public class ArticleVo implements Serializable {
    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章描述
     */
    private String description;


    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章封面图片地址
     */
    private String coverUrl;

    /**
     * 创建时间（文章发布时间）
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 作者
     */
    private User createUser;

    private static final long serialVersionUID = 1L;
}
