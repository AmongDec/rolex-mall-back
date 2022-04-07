package com.rolex.mall.controller.shopcar;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rolex.mall.mapper.shopcar.ShopCarMapper;
import com.rolex.mall.mapper.shopcar.ShopMidCarMapper;
import com.rolex.mall.pojo.collect.CollectVO;
import com.rolex.mall.pojo.order.OrderVO;
import com.rolex.mall.pojo.shopcar.Car;
import com.rolex.mall.pojo.shopcar.GoodCar;
import com.rolex.mall.pojo.shopcar.MidCar;
import com.rolex.mall.pojo.shopcar.ShopcarVO;
import com.rolex.mall.util.RatingUtil;
import com.rolex.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/shopcar")
public class ShopCarController {

    @Autowired
    private ShopCarMapper shopCarMapper;

    @Autowired
    private ShopMidCarMapper shopMidCarMapper;

    @Autowired
    private RatingUtil  ratingUtil;


    /**
     * 根据用户id查询该用户购物车内的商品
     * @param orderVO
     * @return
     */
    @RequestMapping(value = "/find" , method = RequestMethod.POST)
    public HashMap<String , Object> find(@RequestBody OrderVO orderVO){
        if(orderVO == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }
        List<GoodCar> list = shopCarMapper.findcar(orderVO);
        if(list == null){
            return Result.parse("未找到商品" , Result.SUCCESS_CODE);
        }
        return Result.parse(list , Result.SUCCESS_CODE);
    }

    /**
     * 根据用户id , 商品id 添加商品进入购物车
     * @param shopcarVO
     * @return
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public HashMap<String , Object> add(@RequestBody ShopcarVO shopcarVO){
        if(shopcarVO == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }

        //1、判断这个用户是否有购物车
        LambdaQueryWrapper<Car> wrapper = new  LambdaQueryWrapper<>();
        wrapper.eq(Car::getUId , shopcarVO.getUid());
        if(shopCarMapper.selectOne(wrapper) == null){
            //说明这个用户没有购物车 , 那么就创建一个
            Car car = new Car();
            car.setUId(shopcarVO.getUid());
            shopCarMapper.insert(car);
        }
        //2、获得该用户的购物车
        Car car = shopCarMapper.selectOne(wrapper);
        //3、到这里该用户拥有了购物车，那么开始添加商品
        //4、首先判断购物车内是否有该商品
        LambdaQueryWrapper<MidCar> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(MidCar::getCId , car.getCId());
        wrapper1.eq(MidCar::getGId , shopcarVO.getGid());
        if(shopMidCarMapper.selectOne(wrapper1) != null){
            //说明购物车内有该商品 ， 有则数量+1
            shopMidCarMapper.updatemcnumber(shopcarVO.getGid(),car.getCId());
            return Result.parse("购物车内已有该商品，数量+1成功" , Result.SUCCESS_CODE);
        }else{
            //说明购物车内没有该商品 ， 则添加到购物车
            MidCar midCar = new MidCar();
            midCar.setCId(car.getCId());
            midCar.setMcNumber(1);
            midCar.setGId(shopcarVO.getGid());
            shopMidCarMapper.insert(midCar);
            //添加评分
            ratingUtil.createRating( shopcarVO.getUid(),0.5,shopcarVO.getGid());
            return Result.parse("添加成功" , Result.SUCCESS_CODE);
        }
    }

    /**
     * 删除在购物车内的商品
     * @param collectVO
     * @return
     */
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    public HashMap<String , Object> delete(@RequestBody CollectVO collectVO){
        if(collectVO == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }
        shopCarMapper.deleteByVo(collectVO);
        return Result.parse("删除成功" , Result.SUCCESS_CODE);
    }



}
