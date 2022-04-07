package com.rolex.mall.pojo.good;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "ems",type = "_doc", shards = 1, replicas = 0)
public class ESGood {
    @Field(type = FieldType.Long)
    private Integer gId;//商品 id
    @Field(type = FieldType.Text)
    private String gName;//商品名称
    @Field(type = FieldType.Float)
    private Double gPrice;//商品价格
    @Field(type = FieldType.Text)
    private String gVersion;//商品型号
    @Field(type = FieldType.Text)
    private String gMaterial;//商品材质
    @Field(type = FieldType.Long)
    private Integer clsId;//商品类别id
    @Field(type = FieldType.Long)
    private Integer gState;//商品状态（0下架，1上架）
    @Field(type = FieldType.Text)
    private String gMainPic;//主图片
    @Field(type = FieldType.Text)
    private String gBgPic;//背景图片

}
