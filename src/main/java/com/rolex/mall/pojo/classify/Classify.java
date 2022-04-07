package com.rolex.mall.pojo.classify;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("classify")
public class Classify {
    /**
     * 地址表
     */
    @TableId(type = IdType.AUTO)
    private Integer clsId;
    private String clsName;
    private String clsPic;
}
