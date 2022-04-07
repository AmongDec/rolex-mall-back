package com.rolex.mall.pojo.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchListVO {
    List<Map<String , Object>> listmap;
    Long count;
}
