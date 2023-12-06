package zgq.cool.blogbackend.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GQ
 * @home <href="http://www.zgq.cool"/>
 * @date 2023 12/02 23:02
 * @description
 */
@Data
public class ArticleCategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
