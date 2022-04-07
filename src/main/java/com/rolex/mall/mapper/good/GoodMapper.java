package com.rolex.mall.mapper.good;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rolex.mall.pojo.good.GoodTable;
import org.apache.ibatis.annotations.Param;

public interface GoodMapper extends BaseMapper<GoodTable> {
    void addone(@Param("id")Integer gid , @Param("mainpic")String mainpic);
    void addonedetail(@Param("id")Integer gid , @Param("did")Integer did);
}
