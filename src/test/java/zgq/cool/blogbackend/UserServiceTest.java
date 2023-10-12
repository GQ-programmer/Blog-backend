package zgq.cool.blogbackend;
import java.util.Date;

import zgq.cool.blogbackend.model.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zgq.cool.blogbackend.service.UserService;

import javax.annotation.Resource;

/**
 * @Author 孑然
 * @Date 2022 12/03 23:11
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testUser(){
        User user = new User();
        user.setUsername("孑然1");
        user.setPassword("123456");
        user.setDescription("学习编程中~");
        user.setAvatarUrl("");
        user.setEmail("3213821843@qq.com");
        user.setUserRole(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        userService.save(user);
    }
}
