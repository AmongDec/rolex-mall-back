package com.rolex.mall.pojo.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressVO {
    private Integer addid;
    private String uid;
    private String addCity;
    private String addDetail;
    private String addArea;
    private String addProvince;
    private String addPhone;
    private String addGender;
    private String addXing;
    private String addMing;
}
