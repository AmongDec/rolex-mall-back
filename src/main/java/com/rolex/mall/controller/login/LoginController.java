package com.rolex.mall.controller.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rolex.mall.mapper.login.UserMapper;
import com.rolex.mall.pojo.login.User;
import com.rolex.mall.pojo.login.UserVo;
import com.rolex.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    @Resource
    UserMapper userMapper;

    /**
     * 登录接口
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/valandsub" , method = RequestMethod.POST)
    public HashMap<String , Object> valandsub(@RequestBody UserVo userVo){
        if(userVo == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }
        //条件构造
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUPhone , userVo.getPhone());
        lambdaQueryWrapper.eq(User::getUPwd , userVo.getPassword());
        //查询是否存在该用户，有则返回这个用户所有信息
        User u = userMapper.selectOne(lambdaQueryWrapper);
        if(u == null){
            return Result.parse("登录失败" , Result.ERROR_CODE);
        }
        return Result.parse(u.getUId() , Result.SUCCESS_CODE);
    }

    /**
     * 注册接口
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/registe" , method = RequestMethod.POST)
    public HashMap<String , Object> registe(@RequestBody UserVo userVo){
        if(userVo == null || userVo.getPhone() == null || userVo.getPassword() == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUPhone , userVo.getPhone());
        User u = userMapper.selectOne(lambdaQueryWrapper);
        //判断电话号码是否存在
        if(u != null){
            return Result.parse("号码已被注册" , Result.ERROR_CODE);
        }
        //将用户插入到数据库
        u = new User();
        u.setUPhone(userVo.getPhone());
        u.setUPwd(userVo.getPassword());
        userMapper.insert(u);
        return Result.parse("注册成功" , Result.SUCCESS_CODE);
    }



    /**
     * 第一个接口，用作测试
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/find" , method = RequestMethod.POST)
    public HashMap<String , Object> find(@RequestBody UserVo userVo){

        System.out.println(userMapper.selectList(null));
        return Result.parse(null , Result.SUCCESS_CODE);
    }

}
