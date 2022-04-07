package com.rolex.mall.pojo.shopcar;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("car")
public class Car {
    @TableId(type = IdType.AUTO)
    private Integer cId;//购物车id
    private Integer uId;//用户id
}
