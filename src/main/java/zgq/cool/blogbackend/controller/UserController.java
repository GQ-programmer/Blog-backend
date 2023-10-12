package zgq.cool.blogbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zgq.cool.blogbackend.common.BaseResponse;
import zgq.cool.blogbackend.common.ErrorCode;
import zgq.cool.blogbackend.common.ResultUtils;
import zgq.cool.blogbackend.constant.UserConstant;
import zgq.cool.blogbackend.exception.BusinessException;
import zgq.cool.blogbackend.model.pojo.User;
import zgq.cool.blogbackend.model.request.UserLoginRequest;
import zgq.cool.blogbackend.model.request.UserRegisterRequest;
import zgq.cool.blogbackend.model.request.UserUpdatePsdRequest;
import zgq.cool.blogbackend.model.request.UserUpdateRequest;
import zgq.cool.blogbackend.model.vo.UserVo;
import zgq.cool.blogbackend.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 孑然
 * @Date 2022 12/04 12:31
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5175","http://zgq.cool","http://www.zgq.cool"},allowCredentials = "true")
@RequestMapping("/user")
public class UserController implements UserConstant {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse<User> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.login(userLoginRequest, request);
        return ResultUtils.success(loginUser);
    }

    @PostMapping("/register")
    public BaseResponse<Integer> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int res =  userService.register(userRegisterRequest);
        return ResultUtils.success(res);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logOut")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> current(HttpServletRequest request){
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        User safetyUser = userService.getSafetyUser(loginUser);
        return ResultUtils.success(safetyUser);
    }
    @GetMapping("/get")
    public BaseResponse<User> getUserById(long userId) {
        if (userId <  1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不合法!");
        }
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest user, HttpServletRequest request) {
        if (user == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Boolean res = userService.updateUser(user, request);
        return ResultUtils.success(res);
    }

    @PostMapping("/updatePsd")
    public BaseResponse<Boolean> updatePsd(@RequestBody UserUpdatePsdRequest userUpdatePsdRequest, HttpServletRequest request){
        if (userUpdatePsdRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean res = userService.updatePsd(userUpdatePsdRequest, request);
        return ResultUtils.success(res);
    }
}
