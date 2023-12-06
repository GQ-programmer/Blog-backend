package zgq.cool.blogbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import zgq.cool.blogbackend.model.pojo.ArticleCategory;
import zgq.cool.blogbackend.model.vo.ArticleCategoryVO;
import zgq.cool.blogbackend.service.ArticleCategoryService;
import zgq.cool.blogbackend.mapper.ArticleCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 孑然
* @description 针对表【article_category(文章分类表)】的数据库操作Service实现
* @createDate 2023-12-02 22:57:24
*/
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory>
    implements ArticleCategoryService{

    @Override
    public List<ArticleCategoryVO> queryList() {
        return baseMapper.queryList();
    }
}




