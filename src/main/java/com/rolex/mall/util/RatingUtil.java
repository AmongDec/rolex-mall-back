package com.rolex.mall.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rolex.mall.mapper.rating.RatingMapper;
import com.rolex.mall.pojo.rating.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RatingUtil {

    @Autowired
    private RatingMapper ratingMapper;


    /**
     * 生成评分记录
     */
    public void createRating(Integer uid , Double rating , Integer gid){
        //判断该用户是否有该商品的评分记录
        LambdaQueryWrapper<Rating> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Rating::getMovieId , gid);
        wrapper.eq(Rating::getUserId , uid);


        Rating history = ratingMapper.selectOne(wrapper);
        if(history == null){
            //说明没有对这个商品评过分，创建一个评分记录
            Rating ra = new Rating();
            ra.setRating(rating);
            ra.setUserId(uid);
            ra.setRtimestamp(String.valueOf(new Date().getTime()));
            ra.setMovieId(gid);
            ratingMapper.insert(ra);
        }else{
            //说明有，则只需要修改即可
            history.setRating(history.getRating()+rating);
            if(history.getRating() > 5.0){
                history.setRating(5.0d);
            }
            history.setRtimestamp(String.valueOf(new Date().getTime()));
            System.out.println(history);
            ratingMapper.updateById(history);
        }
    }
}
