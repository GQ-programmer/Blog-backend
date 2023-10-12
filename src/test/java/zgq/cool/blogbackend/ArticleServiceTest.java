package zgq.cool.blogbackend;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zgq.cool.blogbackend.model.pojo.Article;
import zgq.cool.blogbackend.service.ArticleService;

import javax.annotation.Resource;

/**
 * @Author 孑然
 * @Date 2022 12/03 23:43
 */
@SpringBootTest
class ArticleServiceTest {

    @Resource
    private ArticleService articleService;


    @Test
    void testArticle(){

        for (int i = 1; i < 21; i++) {

            Article article = new Article();
            article.setId(0L);
            article.setTitle("毕业设计"+i);
            article.setDescription("毕业设计简单描述"+i);
            article.setUserId(1l);
            article.setContent("毕业设计文章内容"+i);
            article.setCoverUrl("");
            article.setCreateTime(new Date());
            article.setUpdateTime(new Date());
            article.setIsDelete(0);
            articleService.save(article);
        }

    }

    //@Test
    //void test1() {
    //    Thread.MAX_PRIORITY
    //}
}
