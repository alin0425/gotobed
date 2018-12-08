package com.neuedu.service;

import com.neuedu.commen.ServerResponse;
import com.neuedu.entity.User;

import javax.servlet.http.HttpSession;

public interface UserService {

    // 注册
    ServerResponse register(User user);
    // 登录
    ServerResponse login(String username,String password);
    // 检查用户名、邮箱是否有效
    ServerResponse checkvalid(String str,String type);
    // 根据用户名查找问题
    ServerResponse forget_get_question(String username);
    // 提交答案
    ServerResponse forget_check_answer(String username,String question,String answer);
    // 修改密码
    ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken);
    // 登录状态改密
    ServerResponse reset_password(User user, String passwordOld, String passwordNew);
}
