package zgq.cool.blogbackend.mapper;

import zgq.cool.blogbackend.model.pojo.ArticleCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import zgq.cool.blogbackend.model.vo.ArticleCategoryVO;

import java.util.List;

/**
* @author 孑然
* @description 针对表【article_category(文章分类表)】的数据库操作Mapper
* @createDate 2023-12-02 22:57:24
* @Entity zgq.cool.blogbackend.model.pojo.ArticleCategory
*/
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    List<ArticleCategoryVO> queryList();
}




