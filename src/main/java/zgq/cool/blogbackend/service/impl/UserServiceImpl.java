package zgq.cool.blogbackend.service.impl;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import zgq.cool.blogbackend.common.ErrorCode;
import zgq.cool.blogbackend.constant.UserConstant;
import zgq.cool.blogbackend.exception.BusinessException;
import zgq.cool.blogbackend.mapper.UserMapper;
import zgq.cool.blogbackend.model.pojo.Article;
import zgq.cool.blogbackend.model.pojo.User;
import zgq.cool.blogbackend.model.request.UserLoginRequest;
import zgq.cool.blogbackend.model.request.UserRegisterRequest;
import zgq.cool.blogbackend.model.request.UserUpdatePsdRequest;
import zgq.cool.blogbackend.model.request.UserUpdateRequest;
import zgq.cool.blogbackend.model.vo.UserRankingVO;
import zgq.cool.blogbackend.model.vo.UserVo;
import zgq.cool.blogbackend.service.ArticleService;
import zgq.cool.blogbackend.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 孑然
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-12-03 23:05:11
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserConstant {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        // 校验用户名
        if (StringUtils.isAnyBlank(username, password)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 用户名大于等于3位
        if (username.length() < 3 || username.length() > 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名3~8位!");
        }
        // 密码大于等于4位
        if (password.length() < 4 || password.length() > 12) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码4~12位!");
        }
        // //账户不能包括特殊字符
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Matcher matcher =  Pattern.compile(regEx).matcher(username);
        if (matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名包含特殊字符!");
        }
        // 查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        User user = this.getOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed,userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.UA_NOT_MATCH_PW, "昵称和密码不匹配!");
        }
        // 记录用户登录态
        System.out.println("将User存入了Session--------------------------------------------------");
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        // 用户脱敏
        User safetyUser = getSafetyUser(user);
        return safetyUser;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setDescription(originUser.getDescription());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setArea(originUser.getArea());
        safetyUser.setBirthday(originUser.getBirthday());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 移出用户登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        System.out.println("将User移出了Session--------------------------------------------------");
        return 1;
    }

    @Override
    public int register(UserRegisterRequest userRegisterRequest) {
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        // 校验是否为空
        if (StringUtils.isAnyBlank(username, password, checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误!");
        }
        // 用户名长度3~8
        if (username.length() < 3 || username.length() > 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"昵称3~8位!");
        }
        // 密码4~12
        if (password.length() < 4 || password.length() > 12) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码4~12位!");
        }
        // 校验两次密码是否一致
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次密码不一致!");
        }
        // 校验用户名是否已经存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User u = this.getOne(queryWrapper);
        if (u != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该昵称已存在!");
        }
        // 封装User对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setGender(null);
        user.setUserRole(0);
        user.setCreateTime(new Date());
        // 插入数据
        boolean save = this.save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入用户数据失败!");
        }
        return 1;
    }

    @Override
    public Boolean updateUser(UserUpdateRequest userReq, HttpServletRequest request) {
        // 存放要修改的键值对
        Map<String, Object> updateMap = new HashMap<>();
        Long id = userReq.getId();
        // 判断id是否合法
        if (id < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 用户名长度3~8
        String username = userReq.getUsername();
        if (StringUtils.isNotBlank(username)) {
            updateMap.put("username", username);
            if (username.length() < 3 || username.length() > 8){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"昵称3~8位!");
            }
        }
        String avatarUrl = userReq.getAvatarUrl();
        if (StringUtils.isNotBlank(avatarUrl)) {
            updateMap.put("avatarUrl", avatarUrl);
        }
        // 性别 只能是男、女
        Character gender = userReq.getGender();
        if (gender != null){
            updateMap.put("gender", gender);
            if ( '男' != gender && '女' != gender){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "性别字段不合法!");
            }
        }
        // 校验邮箱
        String email = userReq.getEmail();
        if (StringUtils.isNotBlank(email)) {
            updateMap.put("email", email);
            String regEmial = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
            //正则表达式的模式 编译正则表达式
            Pattern p = Pattern.compile(regEmial);
            //正则表达式的匹配器
            Matcher m = p.matcher(email);
            //进行正则匹配
            if (!m.matches()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱格式不正确!");
            }
        }
        // 校验个性签名<25
        String description = userReq.getDescription();
        if (StringUtils.isNotBlank(description)) {
            updateMap.put("description", description);
            if (description.length() > 25) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "个性签名25位以内!");
            }
        }
        // 校验出生日期
        String birthday = userReq.getBirthday();
        if (StringUtils.isNotBlank(birthday)) {
            updateMap.put("birthday", birthday);
            String reg_birthday = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
            Pattern p = Pattern.compile(reg_birthday);
            Matcher m = p.matcher(birthday);
            //进行正则匹配
            if (!m.matches()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "出生日期格式不正确!");
            }
        }
        // 校验地区
        String area = userReq.getArea();
        if (StringUtils.isNotBlank(area) ) {
            updateMap.put("area", area);
            if (area.length() > 10) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "地区10位以内!");
            }
        }
        // 头像不用校验，上传头像后，连接必定会变化
        // 判断该id是否存在用户
        User oldUser = this.getById(id);
        if (oldUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该用户不存在!");
        }
        // 判断新值和旧值是否一致
        if (compareObjValue(updateMap, oldUser)) {
            // 相同
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "值未发生变化!");
        }
        // 若要更新用户名，不可与其它用户名相同
        if (StringUtils.isNotBlank(username) && !username.equals(oldUser.getUsername())) {
            // username发生了变化
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User u = this.getOne(queryWrapper);
            if (u != null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "该昵称已存在!");
            }
        }

        User newUser = new User();
        BeanUtils.copyProperties(userReq, newUser);
        // 更新数据库
        boolean res = this.updateById(newUser);
        if (!res) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新用户失败!");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return res;
    }

    @Override
    public boolean updatePsd(UserUpdatePsdRequest userUpdatePsdRequest, HttpServletRequest request) {
        // 校验是否为空
        Long id = userUpdatePsdRequest.getId();
        if (id < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String newPassword = userUpdatePsdRequest.getNewPassword();
        String oldPassword = userUpdatePsdRequest.getOldPassword();
        String checkPassword = userUpdatePsdRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(newPassword, oldPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 校验密码格式是否正确
        // 密码4~12
        if (oldPassword.length() < 4 || oldPassword.length() > 12) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码4~12位!");
        }
        if (newPassword.length() < 4 || newPassword.length() > 12) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码4~12位!");
        }
        if (!newPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入密码不一致!");
        }
        // 新密码和原始密码不可相同
        if (newPassword.equals(oldPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"新密码与原始密码相同!");
        }
        // 原始密码是否正确
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", id);
        userQueryWrapper.eq("password", oldPassword);
        User user = this.getOne(userQueryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "原始密码不正确!");
        }
        // 更新密码
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setPassword(newPassword);
        boolean res = this.updateById(updateUser);
        if (!res) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改密码错误!");
        }
        // 移出用户登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return res;
    }

    @Override
    public List<UserRankingVO> listByTopFive() {
        return userMapper.listByTopFive();
    }


    public boolean compareObjValue(Map<String, Object> updateMap, User oldUser) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", oldUser.getUsername());
        userMap.put("avatarUrl", oldUser.getAvatarUrl());
        userMap.put("gender", oldUser.getGender());
        userMap.put("email", oldUser.getEmail());
        userMap.put("description", oldUser.getDescription());
        userMap.put("birthday", oldUser.getBirthday());
        userMap.put("area", oldUser.getArea());

        Set<String> keySet = updateMap.keySet();
        for (String key : keySet) {
            // 其中有一组值不同，就停止比较
            if (key.equals("gender")) {
                if (updateMap.get(key) != userMap.get(key)){
                    return false;
                }
                continue;
            }
            if (!updateMap.get(key).equals(userMap.get(key))){
                return false;
            }
        }
        return true;
    }
}




