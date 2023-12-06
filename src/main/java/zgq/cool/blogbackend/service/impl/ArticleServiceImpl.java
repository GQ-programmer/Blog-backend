package zgq.cool.blogbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import zgq.cool.blogbackend.common.ErrorCode;
import zgq.cool.blogbackend.constant.UserConstant;
import zgq.cool.blogbackend.exception.BusinessException;
import zgq.cool.blogbackend.mapper.ArticleCategoryMapper;
import zgq.cool.blogbackend.mapper.ArticleMapper;
import zgq.cool.blogbackend.model.pojo.Article;
import zgq.cool.blogbackend.model.pojo.ArticleCategory;
import zgq.cool.blogbackend.model.pojo.User;
import zgq.cool.blogbackend.model.request.ArticleAddRequest;
import zgq.cool.blogbackend.model.request.ArticleUpdateRequest;
import zgq.cool.blogbackend.model.vo.ArticleVo;
import zgq.cool.blogbackend.model.vo.SearchArticleVo;
import zgq.cool.blogbackend.service.ArticleService;
import zgq.cool.blogbackend.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 孑然
* @description 针对表【article(文章)】的数据库操作Service实现
* @createDate 2022-12-03 23:39:33
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService, UserConstant {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public Page<ArticleVo> listPage(long currentPageNum, long pageSize, long articleCategoryId) {
        if (currentPageNum < 1 || pageSize < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("createTime");
        if (articleCategoryId != 0) {
            queryWrapper.eq("articleCategoryId", articleCategoryId);
        }
        Page<Article> articlePage = this.page(new Page<Article>(currentPageNum, pageSize), queryWrapper);
        // 得到分页对象中的文章列表
        List<Article> articleList = articlePage.getRecords();
        List<ArticleVo> articleVoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(articleList)) {

            // 得到作者列表
            List<Long> userIdList = articleList.stream().map(Article::getUserId).collect(Collectors.toList());

            // 关联查询出每个文章对应的作者信息
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.in("id", userIdList);
            List<User> userList = userService.list(userQueryWrapper);

            // 循环取出article，得到userId，在userList中找到对应user，放入articleVo对象中
            for (Article article : articleList) {
                ArticleVo articleVo = new ArticleVo();
                BeanUtils.copyProperties(article, articleVo);
                Long articleUserId = article.getUserId();
                for (User user : userList) {
                    if (user.getId().equals(articleUserId)) {
                        articleVo.setCreateUser(user);
                        break;
                    }
                }
                articleVoList.add(articleVo);
            }

        }
        Page<ArticleVo> articleVoPage = new Page<>();
        articleVoPage.setRecords(articleVoList);
        articleVoPage.setTotal(articlePage.getTotal());
        articleVoPage.setSize(articlePage.getSize());
        articleVoPage.setCurrent(articlePage.getCurrent());
        articleVoPage.setPages(articlePage.getPages());
        return articleVoPage;
    }

    @Override
    public long addArticle(ArticleAddRequest articleAddRequest, HttpServletRequest request) {
        if (articleAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Article article = new Article();
        // 获取当前登录用户
        //1. 未登录不可发布文章
        User loginUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        article.setUserId(loginUser.getId());
        //2. 文章名称大于等于5字
        String title = articleAddRequest.getTitle();
        if (title.length() < 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章标题过短,需超过5字!");
        }
        article.setTitle(title);
        //3. 文章描述大于等于20 字
        String description = articleAddRequest.getDescription();
        if (description.length() < 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章摘要过短,需超过10字!");
        }
        article.setDescription(description);
        //4. 文章内容要超过50字
        String content = articleAddRequest.getContent();
        if (content.length() < 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章内容过短,需超过30字!");
        }
        article.setContent(content);
        //5. 必须包含封面链接地址
        String coverUrl = articleAddRequest.getCoverUrl();
        if (StringUtils.isBlank(coverUrl)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章封面图URL为空!");
        }
        // 查询分类
        Long articleCategoryId = articleAddRequest.getArticleCategoryId();
        ArticleCategory articleCategory = articleCategoryMapper.selectById(articleCategoryId);
        if (articleCategory == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类id不存在!");
        }
        article.setArticleCategoryId(articleCategory.getId());
        article.setArticleCategoryName(articleCategory.getName());
        article.setCoverUrl(coverUrl);
        article.setCreateTime(new Date());
        boolean res = this.save(article);
        if (!res) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文章保存失败!");
        }
        // 返回文章Id
        return article.getId();
    }

    @Override
    public Page<ArticleVo> listPageByUserId(long currentPageNum, long pageSize, long userId) {
        if (currentPageNum < 1 || pageSize < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (userId < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("updateTime");
        queryWrapper.eq("userId", userId);
        Page<Article> articlePage = this.page(new Page<Article>(currentPageNum, pageSize), queryWrapper);
        // 得到分页对象中的文章列表
        List<Article> articleList = articlePage.getRecords();
        if (CollectionUtils.isEmpty(articleList)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<ArticleVo> articleVoList = new ArrayList<>();

        for (Article article : articleList) {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVoList.add(articleVo);
        }
        Page<ArticleVo> articleVoPage = new Page<>();
        articleVoPage.setRecords(articleVoList);
        articleVoPage.setTotal(articlePage.getTotal());
        articleVoPage.setSize(articlePage.getSize());
        articleVoPage.setCurrent(articlePage.getCurrent());
        articleVoPage.setPages(articlePage.getPages());
        return articleVoPage;
    }

    @Override
    public boolean updateArticle(ArticleUpdateRequest articleUpdateRequest, HttpServletRequest request) {
        // 校验参数是否为空
        Long id = articleUpdateRequest.getId();
        if (id != null && id < 1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 未登录不可更新文章
        User loginUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        String title = articleUpdateRequest.getTitle();
        String description = articleUpdateRequest.getDescription();
        String content = articleUpdateRequest.getContent();
        String coverUrl = articleUpdateRequest.getCoverUrl();
        Long articleCategoryId = articleUpdateRequest.getArticleCategoryId();
        if (StringUtils.isAnyBlank(title, description, content, coverUrl)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Article article = new Article();
        article.setId(id);
        if (title.length() < 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章标题过短,需超过5字!");
        }
        article.setTitle(title);
        //3. 文章描述大于等于20 字
        if (description.length() < 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章摘要过短,需超过10字!");
        }
        article.setDescription(description);
        //4. 文章内容要超过50字
        if (content.length() < 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章内容过短,需超过30字!");
        }
        article.setContent(content);
        //5. 必须包含封面链接地址
        if (StringUtils.isBlank(coverUrl)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章封面图URL为空!");
        }
        article.setCoverUrl(coverUrl);

        // 查询分类
        ArticleCategory articleCategory = articleCategoryMapper.selectById(articleCategoryId);
        if (articleCategory == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类id不存在!");
        }
        article.setArticleCategoryId(articleCategory.getId());
        article.setArticleCategoryName(articleCategory.getName());
        // todo 优化比较新值和旧值是否相等

        boolean res = this.updateById(article);
        if (!res){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新文章信息错误!");
        }
        return res;
    }

    @Override
    public List<SearchArticleVo> searchArticle(long searchNum, String searchText) {
        if (searchNum < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(searchText)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<SearchArticleVo> searchArticleVo = articleMapper.searchByText(searchNum, searchText);
        return searchArticleVo;
    }


}




