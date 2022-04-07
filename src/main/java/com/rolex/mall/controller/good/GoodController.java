package com.rolex.mall.controller.good;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rolex.mall.mapper.good.GoodDetailMapper;
import com.rolex.mall.mapper.good.GoodMapper;
import com.rolex.mall.pojo.good.GoodAndDetail;
import com.rolex.mall.pojo.good.GoodDetail;
import com.rolex.mall.pojo.good.GoodTable;
import com.rolex.mall.pojo.good.GoodVO;
import com.rolex.mall.util.RatingUtil;
import com.rolex.mall.util.Result;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private GoodDetailMapper goodDetailMapper;

    @Autowired
    private RatingUtil ratingUtil;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @RequestMapping(value = "/findone" , method = RequestMethod.POST)
    public HashMap<String , Object> findone(@RequestBody GoodVO goodVO) throws IOException {


        if(goodVO.getUid()!=null){
            //添加评分
            ratingUtil.createRating(Integer.valueOf(goodVO.getUid()) , 0.2 , Integer.valueOf(goodVO.getUid()));
        }
        //根据商品id，查询商品的大致信息
        LambdaQueryWrapper<GoodTable> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(GoodTable::getGId , goodVO.getGid());
        GoodTable goodTable = goodMapper.selectOne(wrapper1);
        //根据商品id，查询商品的详细信息
        LambdaQueryWrapper<GoodDetail> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(GoodDetail::getGId , goodVO.getGid());
        GoodDetail goodDetail = goodDetailMapper.selectOne(wrapper2);

        GoodAndDetail goodAndDetail = new GoodAndDetail(goodTable , goodDetail);
        if(goodDetail == null || goodTable == null){
            return Result.parse("不存在该商品" , Result.ERROR_CODE);
        }

        //搜索度+1
        //数据库+1
        goodTable.setGCount(goodTable.getGCount()+1);
        goodMapper.updateById(goodTable);

        //es+1
        SearchRequest searchRequest = new SearchRequest("rolex_good");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("gId", goodVO.getGid()));
        searchSourceBuilder.size(2);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for(SearchHit s:searchHits){
            String docId = s.getId();
            UpdateRequest request = new UpdateRequest();
            request.index("rolex_good");
            request.id(docId);
            request.doc(JSON.toJSONString(goodTable), XContentType.JSON);
            UpdateResponse resp = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            System.out.println(resp.getResult());
        }

        return Result.parse(goodAndDetail , Result.SUCCESS_CODE);
    }

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public HashMap<String , Object> add(@RequestBody GoodVO goodVO){
        Integer id = Integer.valueOf(goodVO.getGid());
        goodMapper.addonedetail(id , (id-1000000));
        return Result.parse("成功插入" , Result.SUCCESS_CODE);
    }
}
