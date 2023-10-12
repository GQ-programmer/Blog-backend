package zgq.cool.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import zgq.cool.blogbackend.model.pojo.Article;
import zgq.cool.blogbackend.model.vo.SearchArticleVo;

import java.util.List;

/**
* @author 孑然
* @description 针对表【article(文章)】的数据库操作Mapper
* @createDate 2022-12-03 23:39:33
* @Entity generator.pojo.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    List<SearchArticleVo> searchByText(long searchNum, String searchText);
}




