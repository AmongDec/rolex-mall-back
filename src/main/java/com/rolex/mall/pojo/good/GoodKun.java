package com.rolex.mall.pojo.good;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodKun {
    private GoodRating g1;
    private GoodRating g2;
    private Double srcprice;//原价
    private Double desprice;//现价
}
