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
@TableName("midorder")
public class MidOrder {
    /**
     * 订单中间表
     */
    @TableId(type = IdType.AUTO)
    private Integer moId;//订单中间表id
    private Integer oId;//订单表
    private Integer gId;//商品id
    private Integer moNumber;//该商品在该订单中的数量
}
