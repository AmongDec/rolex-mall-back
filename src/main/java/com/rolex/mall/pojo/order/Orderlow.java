package com.rolex.mall.pojo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orderlow {
    private Integer oid;//订单id
    private Integer uid;//用户id
    private Integer addid;//地址id
    private String ostatus;//订单状态
    private String otime;//订单产生的时间
    private Double ototalprice;//总价格
}
