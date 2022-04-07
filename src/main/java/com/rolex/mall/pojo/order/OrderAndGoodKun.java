package com.rolex.mall.pojo.order;

import com.rolex.mall.pojo.good.GoodKun;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAndGoodKun {
    private Orderlow oneorder;
    private GoodKun goodlist;
}
