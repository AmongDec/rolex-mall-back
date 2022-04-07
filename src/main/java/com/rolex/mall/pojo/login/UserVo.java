package com.rolex.mall.pojo.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private String phone;
    private String password;
}
