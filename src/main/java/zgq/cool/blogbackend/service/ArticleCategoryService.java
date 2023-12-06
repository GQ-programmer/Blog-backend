package zgq.cool.blogbackend.service;

import zgq.cool.blogbackend.model.pojo.ArticleCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import zgq.cool.blogbackend.model.vo.ArticleCategoryVO;

import java.util.List;

/**
* @author 孑然
* @description 针对表【article_category(文章分类表)】的数据库操作Service
* @createDate 2023-12-02 22:57:24
*/
public interface ArticleCategoryService extends IService<ArticleCategory> {

    List<ArticleCategoryVO> queryList();

}
