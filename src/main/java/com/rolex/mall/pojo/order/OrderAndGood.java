package com.rolex.mall.pojo.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rolex.mall.pojo.good.Good;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class OrderAndGood {
    private Order oneorder;
    private List<Good> goodlist;
}
