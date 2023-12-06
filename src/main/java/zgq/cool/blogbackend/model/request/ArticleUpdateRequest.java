package zgq.cool.blogbackend.model.request;

import lombok.Data;

/**
 * @Author 孑然
 * @Date 2022 12/14 16:22
 */
@Data
public class ArticleUpdateRequest {

    private Long id;

    /**
     * 分类id
     */
    private Long articleCategoryId;
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
}
