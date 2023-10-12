package zgq.cool.blogbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zgq.cool.blogbackend.model.pojo.User;
import zgq.cool.blogbackend.model.request.UserLoginRequest;
import zgq.cool.blogbackend.model.request.UserRegisterRequest;
import zgq.cool.blogbackend.model.request.UserUpdatePsdRequest;
import zgq.cool.blogbackend.model.request.UserUpdateRequest;
import zgq.cool.blogbackend.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author 孑然
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2022-12-03 23:05:11
*/
public interface UserService extends IService<User> {

    User login(UserLoginRequest userLoginRequest, HttpServletRequest request);

    User getSafetyUser(User originUser);

    int userLogout(HttpServletRequest request);

    int register(UserRegisterRequest userRegisterRequest);

    Boolean updateUser(UserUpdateRequest user, HttpServletRequest request);

    boolean updatePsd(UserUpdatePsdRequest userUpdatePsdRequest, HttpServletRequest request);


}
