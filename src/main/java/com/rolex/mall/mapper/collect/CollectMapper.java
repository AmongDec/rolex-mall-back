package com.rolex.mall.mapper.collect;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rolex.mall.pojo.collect.Collect;
import com.rolex.mall.pojo.good.GoodTable;
import com.rolex.mall.pojo.order.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectMapper extends BaseMapper<Collect> {
    List<GoodTable> findCollectList(@Param("vo")OrderVO orderVO);
}
