package com.rolex.mall.pojo.sms;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("car")
public class SMSVO {
    private String phonenum;
    private String code;
    private String password;
}
