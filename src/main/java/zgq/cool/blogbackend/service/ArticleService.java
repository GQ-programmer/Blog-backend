package zgq.cool.blogbackend.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import zgq.cool.blogbackend.model.pojo.Article;
import zgq.cool.blogbackend.model.request.ArticleAddRequest;
import zgq.cool.blogbackend.model.request.ArticleUpdateRequest;
import zgq.cool.blogbackend.model.vo.ArticleVo;
import zgq.cool.blogbackend.model.vo.SearchArticleVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 孑然
* @description 针对表【article(文章)】的数据库操作Service
* @createDate 2022-12-03 23:39:33
*/
public interface ArticleService extends IService<Article> {

    /**
     * 分页查询文章列表
     *
     * @param pageNum
     * @param currentPageNum
     * @param articleCategoryId
     * @return
     */
    Page<ArticleVo> listPage(long currentPageNum, long pageSize, long articleCategoryId);

    /**
     * 添加文章
     * @param articleAddRequest
     * @param request
     * @return
     */
    long addArticle(ArticleAddRequest articleAddRequest, HttpServletRequest request);

    Page<ArticleVo> listPageByUserId(long currentPageNum, long pageSize, long userId);

    boolean updateArticle(ArticleUpdateRequest articleUpdateRequest, HttpServletRequest request);

    List<SearchArticleVo> searchArticle(long searchNum, String searchText);
}
