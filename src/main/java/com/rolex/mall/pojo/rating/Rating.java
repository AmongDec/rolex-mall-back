package com.rolex.mall.pojo.rating;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("rating")
public class Rating {
    @TableId(type = IdType.AUTO)
    private Integer ratingId;
    private Integer movieId;
    private Integer userId;
    private String rtimestamp;
    private Double rating;
}
