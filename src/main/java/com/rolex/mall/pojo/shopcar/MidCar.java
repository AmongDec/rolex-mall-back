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
@TableName("midcar")
public class MidCar {
    @TableId(type = IdType.AUTO)
    private Integer mcId;//购物车中间表id
    private Integer cId;//购物车id
    private Integer gId;//商品id
    private Integer mcNumber;//购物车中该商品数量
}
