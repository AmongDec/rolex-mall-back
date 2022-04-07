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
@TableName("detail")
public class GoodDetail {
    /**
     * 商品详情表
     */
    @TableId(type = IdType.AUTO)
    private Integer dId;//商品详情id
    private Integer gId;//商品id
    private String dWstructure;//表壳结构
    private String dWdiameter;//表壳直径
    private String dWmaterial;//表壳材质
    private String dWencircle;//表壳外圈
    private String dWcoronal;//上链表冠
    private String dMirror;//镜面
    private String dWaterproof;//防水性能
    private String dCore;//机芯
    private String dChronometer;//精密时计
    private String dSwing;//游丝摆轮组件
    private String dUplink;//上链
    private String dPower;//动力储备
    private String dWatchband;//表带样式
    private String dWatchbandm;//表带材质
    private String dJess;//带扣
    private String dDish;//表盘
    private String dIdentification;//认证

}
