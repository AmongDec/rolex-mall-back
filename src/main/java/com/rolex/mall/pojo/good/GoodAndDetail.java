package com.rolex.mall.pojo.good;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodAndDetail {
    /**
     * 商品总信息
     */
    private GoodTable goodTable;
    private GoodDetail goodDetail;
}
