package zgq.cool.blogbackend.model.request;


import lombok.Data;

import java.io.Serializable;

/**
 * @Author 孑然
 * @Date 2022 12/07 22:31
 */
@Data
public class ArticleAddRequest implements Serializable {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章描述
     */
    private String description;

    /**
     * 作者Id
     */
    private Long userId;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章封面图片地址
     */
    private String coverUrl;

    private static final long serialVersionUID = 1L;
}
