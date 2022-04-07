package com.rolex.mall.pojo.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Order {
    /**
     * 订单表
     */
    @TableId(type = IdType.AUTO)
    private Integer oId;//订单id
    private Integer uId;//用户id
    private Integer addId;//地址id
    private String oStatus;//订单状态
    private String oTime;//订单产生的时间
    private Double oTotalPrice;//总价格
}
