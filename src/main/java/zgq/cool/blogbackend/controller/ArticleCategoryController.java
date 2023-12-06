package zgq.cool.blogbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zgq.cool.blogbackend.common.BaseResponse;
import zgq.cool.blogbackend.common.ResultUtils;
import zgq.cool.blogbackend.model.pojo.ArticleCategory;
import zgq.cool.blogbackend.model.vo.ArticleCategoryVO;
import zgq.cool.blogbackend.service.ArticleCategoryService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author GQ
 * @home <href="http://www.zgq.cool"/>
 * @date 2023 12/02 23:00
 * @description
 */
@RestController
@RequestMapping("/articleCategory")
@CrossOrigin(origins = {"http://localhost:5175", "http://zgq.cool", "http://www.zgq.cool"}, allowCredentials = "true")
public class ArticleCategoryController {

    @Resource
    private ArticleCategoryService articleCategoryService;

    @GetMapping("/list")
    public BaseResponse<List<ArticleCategoryVO>> list() {
        List<ArticleCategoryVO> list = articleCategoryService.queryList();
        return ResultUtils.success(list);
    }
}
