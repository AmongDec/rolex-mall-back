package com.rolex.mall.controller.classify;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rolex.mall.mapper.classify.ClassifyMapper;
import com.rolex.mall.mapper.good.GoodMapper;
import com.rolex.mall.pojo.classify.Classify;
import com.rolex.mall.pojo.classify.ClassifyVO;
import com.rolex.mall.pojo.good.GoodTable;
import com.rolex.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/classify")
public class ClassController {

    @Autowired
    private ClassifyMapper classifyMapper;
    @Autowired
    private GoodMapper goodMapper;

    /**
     * 查询所有的分类
     * @return
     */
    @RequestMapping(value = "/find" ,method = RequestMethod.POST)
    public HashMap<String , Object> find(){
        List<Classify> list = classifyMapper.selectList(null);
        return Result.parse(list , Result.SUCCESS_CODE);
    }

    /**
     * 根据传过来的分类id，查询商品列表
     * @return
     */
    @RequestMapping(value = "/findbyid" ,method = RequestMethod.POST)
    public HashMap<String , Object> findbyid(@RequestBody ClassifyVO classifyVO){
        LambdaQueryWrapper<GoodTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodTable::getClsId , classifyVO.getClsid());
        List<GoodTable> list = goodMapper.selectList(wrapper);
        return Result.parse(list , Result.SUCCESS_CODE);
    }

}
