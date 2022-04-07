package com.rolex.mall.mapper.login;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rolex.mall.pojo.login.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper extends BaseMapper<User> {
    public void updateByPhone(@Param("phone") String phone , @Param("pass") String pass);
}
