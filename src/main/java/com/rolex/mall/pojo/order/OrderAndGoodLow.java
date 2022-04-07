package com.rolex.mall.pojo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAndGoodLow {
    private Orderlow oneorder;
    private List<OrderGood> goodlist;
}
