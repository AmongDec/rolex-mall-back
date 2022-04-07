package com.rolex.mall.controller.collect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rolex.mall.mapper.collect.CollectMapper;
import com.rolex.mall.pojo.collect.Collect;
import com.rolex.mall.pojo.collect.CollectVO;
import com.rolex.mall.pojo.good.GoodTable;
import com.rolex.mall.pojo.order.OrderVO;
import com.rolex.mall.util.RatingUtil;
import com.rolex.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private RatingUtil ratingUtil;

    @RequestMapping(value = "/find" , method = RequestMethod.POST)
    public HashMap<String , Object> find(@RequestBody OrderVO orderVO){
        if(orderVO == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }

        //根据用户id，查询他的收藏夹的商品
        List<GoodTable> list = collectMapper.findCollectList(orderVO);
        System.out.println(list);
        return Result.parse(list , Result.SUCCESS_CODE);
    }

    /**
     * 根据用户id和商品id将商品加入收藏表
     * @param collectVO
     * @return
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public HashMap<String , Object> add(@RequestBody CollectVO collectVO){
        if(collectVO == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }
        //先判断收藏夹是否有该商品
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getGId , collectVO.getGid());
        wrapper.eq(Collect::getUId , collectVO.getUid());
        if(collectMapper.selectOne(wrapper) != null){
            //说明有改商品
            return Result.parse("收藏夹已有该商品，请勿重复添加" , Result.ERROR_CODE);
        }
        //没有该商品，则添加
        Collect c = new Collect();
        c.setGId(collectVO.getGid());
        c.setUId(collectVO.getUid());
        collectMapper.insert(c);
        ratingUtil.createRating(collectVO.getUid() , 0.5 , collectVO.getGid());
        return Result.parse("成功添加" , Result.SUCCESS_CODE);
    }
}
