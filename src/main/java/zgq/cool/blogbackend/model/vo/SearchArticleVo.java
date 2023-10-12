package zgq.cool.blogbackend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 孑然
 * @Date 2022 12/23 22:56
 */
@Data
public class SearchArticleVo implements Serializable {

    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 创建时间（文章发布时间）
     */
    private Date createTime;

    /**
     * 用户头像
     */
    private String userAvataUrl;
}
