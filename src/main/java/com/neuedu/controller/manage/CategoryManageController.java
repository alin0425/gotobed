package com.neuedu.controller.manage;

import com.neuedu.commen.Const;
import com.neuedu.commen.ServerResponse;
import com.neuedu.entity.User;
import com.neuedu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    CategoryService categoryService;

    // 获取品类子节点（平级）
    @RequestMapping("get_category.do")
    public ServerResponse get_category(HttpSession session,Integer id){
        User user=(User) session.getAttribute(Const.CURREMTUSER);
        if (user==null){
            return ServerResponse.createServerResponseByError("用户未登录");
        }
        if (user.getRole()!=0){
            return ServerResponse.createServerResponseByError("无操作权限");
        }
        return categoryService.get_category(id);
    }


    // 增加节点
    @RequestMapping("add_category.do")
    public ServerResponse add_category(HttpSession session,@RequestParam(required=false,defaultValue = "0") Integer parentid,String name){
        User user=(User) session.getAttribute(Const.CURREMTUSER);
        if (user==null){
            return ServerResponse.createServerResponseByError("用户未登录");
        }
        if (user.getRole()!=0){
            return ServerResponse.createServerResponseByError("无操作权限");
        }
        return categoryService.add_category(parentid,name);
    }


    // 修改品类名字
    @RequestMapping("set_category.do")
    public ServerResponse set_category(HttpSession session,Integer id,String name){
        User user=(User) session.getAttribute(Const.CURREMTUSER);
        if (user==null){
            return ServerResponse.createServerResponseByError("用户未登录");
        }
        if (user.getRole()!=0){
            return ServerResponse.createServerResponseByError("无操作权限");
        }
        return categoryService.set_category(id,name);
    }


    // 获取当前分类id及递归子节点
    @RequestMapping("get_deep_category.do")
    public ServerResponse get_deep_category(HttpSession session,Integer id){
        User user=(User)session.getAttribute(Const.CURREMTUSER);
        if (user==null){
            return ServerResponse.createServerResponseByError("用户未登录");
        }
        if (user.getRole()!=0){
            return ServerResponse.createServerResponseByError("无操作权限");
        }
        return categoryService.get_deep_category(id);
    }




}
