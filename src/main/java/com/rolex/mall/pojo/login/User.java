package com.rolex.mall.pojo.login;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    /**
     * 用户表
     */
    @TableId(type = IdType.AUTO)
    private Integer uId;//用户id
    private String uGender;//称谓
    private String uName;//用户名
    private String uCountry;//国家地址
    private String uPhone;//联系电话
    private String uPwd;//密码
    private String uStar;//星座
    private String uHobby;//偏好，爱好
    private String uQuestion;//密保问题
    private String uAnswer;//密保问题答案

}
