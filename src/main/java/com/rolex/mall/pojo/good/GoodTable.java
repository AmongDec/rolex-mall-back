package com.rolex.mall.pojo.good;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("goods")
public class GoodTable {
    /**
     * 商品表
     */
    @TableId(type = IdType.AUTO)
    private Integer gId;//商品 id
    private String gName;//商品名称
    private Double gPrice;//商品价格
    private String gVersion;//商品型号
    private String gMaterial;//商品材质
    private Integer clsId;//商品类别id
    private Integer gState;//商品状态（0下架，1上架）
    private String gMainPic;//主图片
    private String gBgPic;//背景图片
    private Integer gCount;//搜索次数
}
