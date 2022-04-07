package com.rolex.mall.mapper.shopcar;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rolex.mall.pojo.collect.CollectVO;
import com.rolex.mall.pojo.order.OrderVO;
import com.rolex.mall.pojo.shopcar.Car;
import com.rolex.mall.pojo.shopcar.GoodCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCarMapper extends BaseMapper<Car> {
    List<GoodCar> findcar(@Param("vo")OrderVO orderVO);
    void deleteByVo(@Param("vo")CollectVO collectVO);
}
//select * from user where name = 'dasd' and pwd = 'dsadu or 1=1'