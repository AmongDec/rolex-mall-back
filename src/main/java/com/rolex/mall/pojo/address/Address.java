package com.rolex.mall.pojo.address;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("address")
public class Address {
    /**
     * 地址表
     */
    @TableId(type = IdType.AUTO)
    private Integer addId;
    private Integer uId;
    private String addCity;
    private String addDetail;
    private String addArea;
    private String addProvince;
    private String addPhone;
    private String addGender;
    private String addXing;
    private String addMing;
}
