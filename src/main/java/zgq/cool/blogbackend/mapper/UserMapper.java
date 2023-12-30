package zgq.cool.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import zgq.cool.blogbackend.model.pojo.User;
import zgq.cool.blogbackend.model.vo.SearchArticleVo;
import zgq.cool.blogbackend.model.vo.UserRankingVO;

import java.util.List;

/**
* @author 孑然
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2022-12-03 23:05:11
* @Entity generator.pojo.User
*/
public interface UserMapper extends BaseMapper<User> {

    List<UserRankingVO> listByTopFive();
}




