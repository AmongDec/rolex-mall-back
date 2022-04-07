package com.rolex.mall.controller.search;

import com.alibaba.fastjson.JSON;
import com.rolex.mall.mapper.good.GoodMapper;
import com.rolex.mall.pojo.good.GoodTable;
import com.rolex.mall.pojo.search.SearchListVO;
import com.rolex.mall.pojo.search.SearchVO;
import com.rolex.mall.util.JsoupUtil;
import com.rolex.mall.util.Result;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private JsoupUtil jsoupUtil;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 从jd爬取数据，存入数据库
     * @return
     */
    @RequestMapping(value = "/pachongsql" , method = RequestMethod.POST)
    public HashMap<String , Object> find(@RequestBody SearchVO searchVO) throws Exception {
        List<GoodTable> list = jsoupUtil.parse(searchVO.getKeyword());
        for(GoodTable g :list){
            goodMapper.insert(g);
            goodMapper.addonedetail(g.getGId() , g.getGId()-1000000);
        }
        return Result.parse("创建成功" , Result.SUCCESS_CODE);
    }

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public HashMap<String , Object> add() throws Exception {
        //1、从数据库将所有商品信息查询出来
        List<GoodTable> list = goodMapper.selectList(null);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        //2、批量插入
        for(GoodTable g: list){
            bulkRequest.add(
                    new IndexRequest("rolex_good")
                    .source(JSON.toJSONString(g) , XContentType.JSON)
            );
        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return Result.parse( bulk.hasFailures() , Result.SUCCESS_CODE);
    }

    /**
     * 关键字搜索
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search" , method = RequestMethod.POST)
    public HashMap<String , Object> add(@RequestBody SearchVO searchVO) throws Exception {
        //1、判断起始页和页数
        if(searchVO.getPageno() <= 1){
            searchVO.setPageno(1);
        }
        if(searchVO.getSize() <=10){
            searchVO.setSize(10);
        }
        //2、搜索条件
        SearchRequest request = new SearchRequest("rolex_good");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //3、分页
        builder.from(searchVO.getPageno());
        builder.size(searchVO.getSize());

        //4、构建muli
        MultiMatchQueryBuilder muli = QueryBuilders.multiMatchQuery(searchVO.getKeyword(), "gName", "gName.keyword", "gVersion", "gVersion.keyword", "gMaterial", "gMaterial.keyword");
        builder.query(muli);
        builder.timeout( new TimeValue(10 , TimeUnit.SECONDS));

        request.source(builder);

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);


        List<Map<String , Object>> listmap = new ArrayList<Map<String , Object>>();
        System.out.println(response.getHits().getTotalHits().value);
        for (SearchHit documentFields : response.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
            listmap.add(documentFields.getSourceAsMap());
        }
        if(listmap.size() <= 0){
            return Result.parse( "抱歉未搜索到任何数据" , Result.ERROR_CODE);
        }
        return Result.parse( new SearchListVO(listmap
        ,response.getHits().getTotalHits().value), Result.SUCCESS_CODE);
    }

    @PostMapping("/hotkey")
    public HashMap<String , Object> hot() throws IOException {

        //2、搜索条件
        SearchRequest request = new SearchRequest("rolex_good");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //3、分页
        builder.from(1);
        builder.size(10);

        //4、构建muli
        MatchAllQueryBuilder allquery = QueryBuilders.matchAllQuery();
        builder.query(allquery);
        builder.timeout( new TimeValue(10 , TimeUnit.SECONDS));
        SortBuilder datasort = SortBuilders.fieldSort("gCount").order(SortOrder.DESC);
        builder.sort(datasort);
        request.source(builder);


        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);


        List<Map<String , Object>> listmap = new ArrayList<Map<String , Object>>();
        System.out.println(response.getHits().getTotalHits().value);
        for (SearchHit documentFields : response.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
            listmap.add(documentFields.getSourceAsMap());
        }
        if(listmap.size() <= 0){
            return Result.parse( "抱歉未搜索到任何数据" , Result.ERROR_CODE);
        }

        return Result.parse(  new SearchListVO(listmap
                ,response.getHits().getTotalHits().value) , Result.SUCCESS_CODE);
    }

}
