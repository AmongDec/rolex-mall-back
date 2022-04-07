package com.rolex.mall.mapper.shopcar;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rolex.mall.pojo.shopcar.MidCar;
import org.apache.ibatis.annotations.Param;

public interface ShopMidCarMapper extends BaseMapper<MidCar> {
    void updatemcnumber(@Param("gid")Integer gid , @Param("cid")Integer cid);
}
