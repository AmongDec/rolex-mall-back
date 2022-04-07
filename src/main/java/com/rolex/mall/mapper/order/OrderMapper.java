package com.rolex.mall.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rolex.mall.pojo.good.Good;
import com.rolex.mall.pojo.order.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {
    //根据订单id，查找商品列表
    List<Good> findByOid(@Param("oid") Integer i);
}
