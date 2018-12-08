package com.neuedu.controller.portal;

import com.neuedu.commen.Const;
import com.neuedu.commen.ResponseCode;
import com.neuedu.commen.ServerResponse;
import com.neuedu.entity.User;
import com.neuedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/portal/user/")
public class UserController {

    @Autowired
    UserService userService;

    // 登录
    @RequestMapping("login.do")
    public ServerResponse login(HttpSession session,String username, String password){

        ServerResponse serverResponse=userService.login(username,password);
        if (serverResponse.isSuccess()){ //保存登录状态
            session.setAttribute(Const.CURREMTUSER,serverResponse.getData());
        }
        return serverResponse;
    }


    // 注册
    @RequestMapping("register.do")
    public ServerResponse register(User user){

        return userService.register(user);
    }

    // 检查用户名、邮箱
    @RequestMapping("checkvalid.do")
    public ServerResponse checkvalid(String str,String type){

        return userService.checkvalid(str,type);
    }

    // 获取用户登录信息
    @RequestMapping("getuserinfo.do")
    public ServerResponse getuserinfo(HttpSession session){
        Object o=session.getAttribute(Const.CURREMTUSER);
        if (o!=null&&o instanceof User){
            User user=(User)o;
            User responseuser=new User();
            responseuser.setUid(user.getUid());
            responseuser.setUsername(user.getUsername());
            responseuser.setPassword(user.getPassword());
            responseuser.setCreatetime(user.getCreatetime());
            responseuser.setUpdatetime(user.getUpdatetime());
            return ServerResponse.createServerResponseBySucess(null,responseuser);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    // 获取详细信息
    @RequestMapping("getformation.do")
    public ServerResponse getformation(HttpSession session){
        Object o=session.getAttribute(Const.CURREMTUSER);
        if (o!=null&&o instanceof User){
            User user=(User)o;
            return ServerResponse.createServerResponseBySucess(null,user);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }



    // 根据用户名查找问题
    @RequestMapping("forget_get_question.do")
    public ServerResponse forget_get_question(String username){

       return userService.forget_get_question(username);

    }

    // 提交问题答案
    @RequestMapping("forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer){

        return userService.forget_check_answer(username,question,answer);

    }

    // 修改密码
    @RequestMapping("forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken){

        return userService.forget_reset_password(username,passwordNew,forgetToken);
    }


    // 登出
    @RequestMapping("logout.do")
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURREMTUSER);
        return ServerResponse.createServerResponseBySucess();
    }



    // 登录状态改密
    @RequestMapping("reset_password")
    public ServerResponse reset_password(HttpSession session,String passwordOld,String passwordNew){
        Object o=session.getAttribute(Const.CURREMTUSER);
        if (o!=null&&o instanceof User){
            User user=(User)o;
            return userService.reset_password(user,passwordOld,passwordNew);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
}
