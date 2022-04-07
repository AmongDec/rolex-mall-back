package com.rolex.mall.pojo.collect;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("collect")
public class Collect {
    /**
     * 收藏表
     */
    @TableId(type = IdType.AUTO)
    private Integer clId;
    private Integer uId;//用户id
    private Integer gId;//商品id
}
