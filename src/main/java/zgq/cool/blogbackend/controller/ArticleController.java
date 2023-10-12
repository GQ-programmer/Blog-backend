package zgq.cool.blogbackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import zgq.cool.blogbackend.common.BaseResponse;
import zgq.cool.blogbackend.common.ErrorCode;
import zgq.cool.blogbackend.common.ResultUtils;
import zgq.cool.blogbackend.exception.BusinessException;
import zgq.cool.blogbackend.model.pojo.Article;
import zgq.cool.blogbackend.model.pojo.User;
import zgq.cool.blogbackend.model.request.ArticleAddRequest;
import zgq.cool.blogbackend.model.request.ArticleUpdateRequest;
import zgq.cool.blogbackend.model.vo.ArticleVo;
import zgq.cool.blogbackend.model.vo.SearchArticleVo;
import zgq.cool.blogbackend.service.ArticleService;
import zgq.cool.blogbackend.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author 孑然
 * @Date 2022 12/05 18:03
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5175", "http://zgq.cool", "http://www.zgq.cool"}, allowCredentials = "true")
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/listPage")
    public BaseResponse<Page<ArticleVo>> listPage(@RequestParam("currentPageNum") long currentPageNum, @RequestParam("pageSize") long pageSize, HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (currentPageNum < 1 || pageSize < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<ArticleVo> page = articleService.listPage(currentPageNum, pageSize);
        return ResultUtils.success(page);
    }

    @GetMapping("/listPageByUserId")
    public BaseResponse<Page<ArticleVo>> listPageByUserId(@RequestParam("currentPageNum") long currentPageNum,
                                                          @RequestParam("pageSize") long pageSize, long userId, HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (currentPageNum < 1 || pageSize < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<ArticleVo> page = articleService.listPageByUserId(currentPageNum, pageSize, userId);
        return ResultUtils.success(page);
    }

    @PostMapping("/add")
    public BaseResponse<Long> addArticle(@RequestBody ArticleAddRequest articleAddRequest, HttpServletRequest request) {
        if (articleAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long res = articleService.addArticle(articleAddRequest, request);
        return ResultUtils.success(res);
    }

    @GetMapping("/get")
    public BaseResponse<ArticleVo> getOne(long articleId) {
        if (articleId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Article article = articleService.getById(articleId);
        if (article == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "未查询到该文章!");
        }
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        User createUser = userService.getById(article.getUserId());
        if (createUser == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "未查询到作者信息！");
        }
        articleVo.setCreateUser(createUser);
        return ResultUtils.success(articleVo);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateArticle(@RequestBody ArticleUpdateRequest articleUpdateRequest,
                                               HttpServletRequest request) {
        if (articleUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean res = articleService.updateArticle(articleUpdateRequest, request);
        return ResultUtils.success(res);
    }

    @GetMapping("/search")
    public BaseResponse<List<SearchArticleVo>> search(long searchNum, String searchText) {
        if (searchNum < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(searchText)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<SearchArticleVo> searchArticleVos = articleService
                .searchArticle(searchNum, searchText);
        if (CollectionUtils.isEmpty(searchArticleVos)) {
            searchArticleVos = new ArrayList<>();
        }
        return ResultUtils.success(searchArticleVos);
    }
}
