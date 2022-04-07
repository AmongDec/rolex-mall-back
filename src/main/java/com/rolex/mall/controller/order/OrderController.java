package com.rolex.mall.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rolex.mall.mapper.good.GoodMapper;
import com.rolex.mall.mapper.order.MidOrderMapper;
import com.rolex.mall.mapper.order.OrderMapper;
import com.rolex.mall.mapper.shopcar.ShopCarMapper;
import com.rolex.mall.pojo.collect.CollectVO;
import com.rolex.mall.pojo.good.Good;
import com.rolex.mall.pojo.good.GoodTable;
import com.rolex.mall.pojo.order.*;
import com.rolex.mall.pojo.shopcar.ShopcarVO;
import com.rolex.mall.util.RatingUtil;
import com.rolex.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MidOrderMapper midOrderMapper;
    @Autowired
    private ShopCarMapper shopCarMapper;
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private RatingUtil ratingUtil;


    /**
     * 查找所有订单
     * @param orderVO
     * @return
     */
    @RequestMapping(value = "/find" , method = RequestMethod.POST)
    public HashMap<String , Object> find(@RequestBody OrderVO orderVO){
        if(orderVO == null || orderVO.getUid() == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }
        //1、分类将订单查询出来
        List<Order> waitpay = findByCon(orderVO , "待支付");
        List<Order> cancel = findByCon(orderVO , "已取消");
        List<Order> waitrec = findByCon(orderVO , "待收货");
        List<Order> finish = new ArrayList<>();
        //将已取消和合并
        finish.addAll(findByCon(orderVO , "已完成"));
        finish.addAll(cancel);

        //将每个订单的商品查询出来,然后将他放到结果集
        HashMap<String , Object> mydata = new HashMap<>();
        mydata.put("waitpay" , parseOrder(waitpay)==null?"none":parseOrder(waitpay));
        mydata.put("waitrec" , parseOrder(waitrec)==null?"none":parseOrder(waitrec));
        mydata.put("finish" , parseOrder(finish)==null?"none":parseOrder(finish));

        if(waitpay == null && cancel == null && waitrec == null && finish.size() == 0){
            return Result.parse("none" , Result.SUCCESS_CODE);
        }
        System.out.println(mydata);
        return Result.parse(mydata , Result.SUCCESS_CODE);
    }

    /**
     *
     * 根据购物车商品创建订单
     * @param orderAndGood
     * @return
     */
    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    public HashMap<String , Object> create(@RequestBody OrderAndGoodLow orderAndGood){
        if(orderAndGood == null){
            return Result.parse("请求参数为空" , Result.ERROR_CODE);
        }
        //1、先写入orders表
        Order order = new Order();
        order.setAddId(orderAndGood.getOneorder().getAddid());
        order.setOTotalPrice(orderAndGood.getOneorder().getOtotalprice());
        order.setUId(orderAndGood.getOneorder().getUid());
        order.setOTime(String.valueOf(new Date().getTime()));
        order.setOStatus("待支付");
        orderMapper.insert(order);
        //2、再写入midorder表
        for(OrderGood good : orderAndGood.getGoodlist()){
          MidOrder midOrder = new MidOrder();
          midOrder.setOId(order.getOId());
          midOrder.setGId(good.getGid());
          midOrder.setMoNumber(good.getMcNumber());
          midOrderMapper.insert(midOrder);
        }
        //3、删除购物车内对应的商品
        for(OrderGood good : orderAndGood.getGoodlist()){
            //顺便添加评分
            ratingUtil.createRating(order.getUId() , 1.0 , good.getGid());
          CollectVO collectVO = new CollectVO();
          collectVO.setGid(good.getGid());
          collectVO.setUid(orderAndGood.getOneorder().getUid());
          shopCarMapper.deleteByVo(collectVO);
        }

        return Result.parse( order.getOId() , Result.SUCCESS_CODE);
    }

    @RequestMapping(value = "/createkun" , method = RequestMethod.POST)
    public HashMap<String , Object> createkun(@RequestBody OrderAndGoodKun orderAndGoodKun){
        //1、根据商品id查找商品的信息
        LambdaQueryWrapper<GoodTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodTable::getGId , orderAndGoodKun.getGoodlist().getG1().getGoodTable().getGId());
        GoodTable goodTable1 = goodMapper.selectOne(wrapper);

        LambdaQueryWrapper<GoodTable> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(GoodTable::getGId , orderAndGoodKun.getGoodlist().getG2().getGoodTable().getGId());
        GoodTable goodTable2 = goodMapper.selectOne(wrapper2);

        //2、生成订单表信息
        Order order = new Order();
        order.setOTotalPrice(orderAndGoodKun.getOneorder().getOtotalprice());
        order.setAddId(orderAndGoodKun.getOneorder().getAddid());
        order.setOStatus("待支付");
        order.setOTime(String.valueOf(new Date().getTime()));
        order.setUId(orderAndGoodKun.getOneorder().getUid());
        orderMapper.insert(order);

        //3、生成中间订单表
        MidOrder midOrder = new MidOrder();
        midOrder.setOId(order.getOId());
        midOrder.setGId(orderAndGoodKun.getGoodlist().getG1().getGoodTable().getGId());
        midOrder.setMoNumber(1);


        MidOrder midOrder1 = new MidOrder();
        midOrder1.setOId(order.getOId());
        midOrder1.setGId(orderAndGoodKun.getGoodlist().getG2().getGoodTable().getGId());
        midOrder1.setMoNumber(1);
        midOrderMapper.insert(midOrder);
        midOrderMapper.insert(midOrder1);
        ratingUtil.createRating(orderAndGoodKun.getOneorder().getUid() , 1.0 , orderAndGoodKun.getGoodlist().getG2().getGoodTable().getGId());
        ratingUtil.createRating(orderAndGoodKun.getOneorder().getUid() , 1.0 , orderAndGoodKun.getGoodlist().getG1().getGoodTable().getGId());
        return Result.parse( order.getOId() , Result.SUCCESS_CODE);
    }




    /**
     * 根据订单号，查找单个订单所有信息
     * @param orderVO
     * @return
     */
    @RequestMapping(value = "/findone" , method = RequestMethod.POST)
    public HashMap<String , Object> findone(@RequestBody OrderVO orderVO){
        //根据订单号，查询订单所有信息
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOId , orderVO.getOid());
        Order order = orderMapper.selectOne(wrapper);
        List<Good> goodlsit = orderMapper.findByOid(order.getOId());
        return Result.parse(new OrderAndGood(order , goodlsit),Result.SUCCESS_CODE);
    }

    /**
     * 根据用户id ， 商品id生成订单
     */
    @RequestMapping(value = "/createone" , method = RequestMethod.POST)
    public HashMap<String , Object> createone(@RequestBody ShopcarVO shopcarVO){
        //1、根据商品id查找商品的信息
        LambdaQueryWrapper<GoodTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodTable::getGId , shopcarVO.getGid());
        GoodTable goodTable = goodMapper.selectOne(wrapper);

        //2、生成订单表信息
        Order order = new Order();
        order.setOTotalPrice(goodTable.getGPrice());
        order.setAddId(shopcarVO.getAddid());
        order.setOStatus("待支付");
        order.setOTime(String.valueOf(new Date().getTime()));
        order.setUId(shopcarVO.getUid());
        orderMapper.insert(order);

        //3、生成中间订单表
        MidOrder midOrder = new MidOrder();
        midOrder.setOId(order.getOId());
        midOrder.setGId(shopcarVO.getGid());
        midOrder.setMoNumber(1);
        midOrderMapper.insert(midOrder);
        ratingUtil.createRating(shopcarVO.getUid() , 1.0 , shopcarVO.getGid());
        return Result.parse( order.getOId() , Result.SUCCESS_CODE);
    }

    /**
     * 根据状态和用户id查询订单
     * @param orderVO
     * @param state
     * @return
     */
    public List<Order> findByCon(OrderVO orderVO , String state){
        //1、根据用户的id，查询出该用户的订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        //2、设置条件
        queryWrapper.eq(Order::getUId , Integer.valueOf(orderVO.getUid()));
        queryWrapper.eq(Order::getOStatus , state);
        return orderMapper.selectList(queryWrapper);
    }

    /**
     * 订单转换
     * @return
     */
    public List<OrderAndGood> parseOrder(List<Order> list){
        if(list == null){
            return null;
        }

        List<OrderAndGood> result = new ArrayList<>();
        for(Order o : list){
            //根据订单id，查找商品列表
            List<Good> goodlsit = orderMapper.findByOid(o.getOId());
            OrderAndGood orderAndGood = new OrderAndGood();
            orderAndGood.setGoodlist(goodlsit);
            orderAndGood.setOneorder(o);
            result.add(orderAndGood);
        }
        return result;
    }



}
