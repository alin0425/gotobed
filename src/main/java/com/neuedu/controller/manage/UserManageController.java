package com.neuedu.controller.manage;

import com.neuedu.commen.Const;
import com.neuedu.commen.ServerResponse;
import com.neuedu.entity.User;
import com.neuedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    UserService userService;

    // 后台管理员登录
    @RequestMapping("login.do")
    public ServerResponse login(HttpSession session, String username, String password){

        ServerResponse serverResponse=userService.login(username,password);
        if (serverResponse.isSuccess()){ //保存登录状态
            User user=(User)serverResponse.getData();
            if (user.getRole()==1){
                return ServerResponse.createServerResponseByError("无权限");
            }
            session.setAttribute(Const.CURREMTUSER,user);
        }
        return serverResponse;
    }
}
