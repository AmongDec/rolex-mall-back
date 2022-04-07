package com.rolex.mall.pojo.search;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchVO {
    private String keyword;
    private Integer pageno;
    private Integer size;
}
