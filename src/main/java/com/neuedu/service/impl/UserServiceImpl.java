package com.neuedu.service.impl;

import com.neuedu.commen.Const;
import com.neuedu.commen.ResponseCode;
import com.neuedu.commen.ServerResponse;
import com.neuedu.commen.TokenCache;
import com.neuedu.dao.UserMapper;
import com.neuedu.entity.User;
import com.neuedu.service.UserService;
import com.neuedu.util.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse register(User user) {

        // step1:参数非空校验
        if (user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        // step2:用户名唯一
        String username=user.getUsername();
//        int count=userMapper.checkUsername(username);
        /*if (count>0){ //该用户名存在
            return ServerResponse.createServerResponseByError(ResponseCode.EXIXTS_USERNAME.getStatus(),ResponseCode.EXIXTS_USERNAME.getMsg());
        }*/
        ServerResponse serverResponse=checkvalid(username,Const.USERNAME);
        if (!serverResponse.isSuccess()){
            return ServerResponse.createServerResponseByError(ResponseCode.EXIXTS_USERNAME.getStatus(),ResponseCode.EXIXTS_USERNAME.getMsg());
        }

        // step3:邮箱唯一
        String email=user.getEmail();
//        int result=userMapper.checkEmail(email);
        /*if (result>0){
            return ServerResponse.createServerResponseByError(ResponseCode.EXIXTS_EMAIL.getStatus(),ResponseCode.EXIXTS_EMAIL.getMsg());
        }*/
        ServerResponse serverResponseEmail=checkvalid(email,Const.EMAIL);
        if (!serverResponseEmail.isSuccess()){
            return ServerResponse.createServerResponseByError(ResponseCode.EXIXTS_EMAIL.getStatus(),ResponseCode.EXIXTS_EMAIL.getMsg());
        }

        // step4:注册
        user.setRole(Const.USER_ROLE_CUSTOMER);
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        int insertresult=userMapper.insert(user);

        // step5:返回结果
        if (insertresult>0){
            return ServerResponse.createServerResponseBySucess("注册成功");
        }
        return ServerResponse.createServerResponseByError("注册失败");
    }



    @Override
    public ServerResponse login(String username, String password) {

        // step1:参数非空校验
        if (StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByError("用户名不能为空");
        }
        if (StringUtils.isBlank(password)){
            return ServerResponse.createServerResponseByError("密码不能为空");
        }
        // step2:检查username是否存在
        /*int result=userMapper.checkUsername(username);
        if (result<=0){  //不存在
            return ServerResponse.createServerResponseByError("用户名不存在");
        }*/

        ServerResponse serverResponse=checkvalid(username,Const.USERNAME);
        if (serverResponse.isSuccess()){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_EXIXTS_USERNAME.getStatus(),ResponseCode.NOT_EXIXTS_USERNAME.getMsg());
        }
        /**
         * ==================================
         */

        // step3:根据用户名和密码查询
        User user=userMapper.selectByusernameAndpassword(username,MD5Utils.getMD5Code(password));
        if (user==null){  //密码错误
            return ServerResponse.createServerResponseByError("密码错误");
        }
        // step4:处理结果并返回
        user.setPassword("");
        return ServerResponse.createServerResponseBySucess(null,user);

    }

    @Override
    public ServerResponse checkvalid(String str, String type) {

        // step1:参数非空校验
        if (StringUtils.isBlank(str)||StringUtils.isBlank(type)){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        // step2:检查用户名、邮箱是否有效
        if (type.equals(Const.USERNAME)){
            int usernameresult=userMapper.checkUsername(str);
            if (usernameresult>0){
                return ServerResponse.createServerResponseByError(ResponseCode.EXIXTS_USERNAME.getStatus(),ResponseCode.EXIXTS_USERNAME.getMsg());
            }
            return ServerResponse.createServerResponseBySucess("成功");
        }else if(type.equals(Const.EMAIL)){
            int emailresult=userMapper.checkEmail(str);
            if (emailresult>0){
                return ServerResponse.createServerResponseByError(ResponseCode.EXIXTS_EMAIL.getStatus(),ResponseCode.EXIXTS_EMAIL.getMsg());
            }
            return ServerResponse.createServerResponseBySucess("成功");
        }
        // step3:返回结果

        return ServerResponse.createServerResponseByError("type传递有误");
    }

    @Override
    public ServerResponse forget_get_question(String username) {

        // step1:参数非空校验
        if (StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }

        // step2:判断用户是否存在
        ServerResponse serverResponse=checkvalid(username,Const.USERNAME);
        if (serverResponse.getStatus()!=ResponseCode.EXIXTS_USERNAME.getStatus()){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_EXIXTS_USERNAME.getStatus(),ResponseCode.NOT_EXIXTS_USERNAME.getMsg());
        }
        // step3:查询密保问题
        String question=userMapper.selectQuestionByUsername(username);
        if (StringUtils.isBlank(question)){
            return ServerResponse.createServerResponseByError("问题为空");
        }
        // step4:返回结果

        return ServerResponse.createServerResponseBySucess(null,question);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {

        //step1:参数非空校验
        if (StringUtils.isBlank(username)||StringUtils.isBlank(question)||StringUtils.isBlank(answer)){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        //step2:校验答案
        int count=userMapper.checkAnswerByUsernameAndQuestion(username,question,answer);
        if (count<=0){
            return ServerResponse.createServerResponseByError("答案有误");
        }

        // ========= 返回用户唯一标识
        String user_token=UUID.randomUUID().toString();
        TokenCache.put(username,user_token);

        //step3:返回结果
        return ServerResponse.createServerResponseBySucess(null,user_token);
    }

    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew,String forgetToken) {

        // step1:参数非空校验
        if (StringUtils.isBlank(username)||StringUtils.isBlank(passwordNew)||StringUtils.isBlank(forgetToken)){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        // 校验 token
        String token=TokenCache.get(username);
        if (StringUtils.isBlank(token)){
            return ServerResponse.createServerResponseByError("token不存在");
        }
        if (!token.equals(forgetToken)){
            return ServerResponse.createServerResponseByError("token不匹配");
        }

        // step2:更新密码
        int count=userMapper.updatePasswordByUsername(username,MD5Utils.getMD5Code(passwordNew));
        if (count<=0){
            return ServerResponse.createServerResponseByError("修改失败");
        }

        // step3:返回结果
        return ServerResponse.createServerResponseBySucess("修改成功");
    }

    @Override
    public ServerResponse reset_password(User user, String passwordOld, String passwordNew) {

        // step1:参数非空校验
        if (StringUtils.isBlank(passwordNew)||StringUtils.isBlank(passwordOld)){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        // step2:旧密码是否正确
        User userold=userMapper.selectByusernameAndpassword(user.getUsername(),MD5Utils.getMD5Code(passwordOld));
        if (userold==null){
            return ServerResponse.createServerResponseByError("旧密码错误");
        }

        // step3:修改密码
        int count=userMapper.updatePasswordByUsername(user.getUsername(),MD5Utils.getMD5Code(passwordNew));
        if (count<=0){
            return ServerResponse.createServerResponseByError("修改失败");
        }
        // step4:返回结果

        return ServerResponse.createServerResponseBySucess();
    }



}
