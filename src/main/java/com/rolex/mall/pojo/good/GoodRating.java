package com.rolex.mall.pojo.good;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodRating {
    private GoodTable goodTable;
    private Float rating;
}
