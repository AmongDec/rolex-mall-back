package com.rolex.mall.controller.recommend;

import com.rolex.mall.pojo.order.OrderVO;
import com.rolex.mall.util.Result;
import com.rolex.mall.util.UserCF;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private UserCF userCF;


    /**
     * 推荐接口 ， 平均效率2.5s左右 10个邻居 推荐10件商品
     * @param orderVO
     * @return
     */
    @RequestMapping(value = "/find" , method = RequestMethod.POST)
    public HashMap<String , Object> find(@RequestBody OrderVO orderVO)  {
        if(orderVO == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }
        List<RecommendedItem> list = null;
        //解决并发，采用ThreadLocalRandom
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        try {
            list =  userCF.basedMysql(Long.valueOf(orderVO.getUid()));
            if(list == null){
                list =  userCF.basedCSV(Long.valueOf(threadLocalRandom.nextInt(611)));
            }
        } catch (TasteException e) {
            e.printStackTrace();
            return Result.parse("TasteException" , Result.EXCEPTION_CODE);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.parse("IOException" , Result.EXCEPTION_CODE);
        }
        return Result.parse(userCF.parseResult(list) , Result.SUCCESS_CODE);
    }

    /**
     * 未登录的推荐接口
     * @return
     */
    @RequestMapping(value = "/findfirst" , method = RequestMethod.POST)
    public HashMap<String , Object> findfirst()  {
        List<RecommendedItem> list = null;
        //解决并发，采用ThreadLocalRandom
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        try {
            list =  userCF.basedCSV(Long.valueOf(threadLocalRandom.nextInt(611)));
        } catch (TasteException e) {
            e.printStackTrace();
            return Result.parse("TasteException" , Result.EXCEPTION_CODE);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.parse("IOException" , Result.EXCEPTION_CODE);
        }
        return Result.parse(userCF.parseResult(list) , Result.SUCCESS_CODE);
    }


    @RequestMapping(value = "/kun" , method = RequestMethod.POST)
    public HashMap<String , Object> kun(@RequestBody OrderVO orderVO)  {
        if(orderVO == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }
        List<RecommendedItem> list = null;
        //解决并发，采用ThreadLocalRandom
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        try {
            list =  userCF.basedItemCSV(Long.valueOf(orderVO.getUid()));
            if (list == null) {
                list =  userCF.basedItemCSV(Long.valueOf(threadLocalRandom.nextInt(51)));
            }
        } catch (TasteException e) {
            e.printStackTrace();
            return Result.parse("TasteException" , Result.EXCEPTION_CODE);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.parse("IOException" , Result.EXCEPTION_CODE);
        }
        return Result.parse(userCF.parseKun(userCF.parseResult(list)) , Result.SUCCESS_CODE);
    }



}
