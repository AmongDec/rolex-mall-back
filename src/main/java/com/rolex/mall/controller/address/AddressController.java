package com.rolex.mall.controller.address;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.rolex.mall.mapper.address.AddressMapper;
import com.rolex.mall.pojo.address.Address;
import com.rolex.mall.pojo.address.AddressVO;
import com.rolex.mall.pojo.order.OrderVO;
import com.rolex.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 根据用户id，查询用户的地址 100  xiancheng 50
     * @param orderVO
     * @return
     */
    @RequestMapping(value = "/find" ,method = RequestMethod.POST)
    public HashMap<String , Object> find(@RequestBody OrderVO orderVO){
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUId , orderVO.getUid());

        List<Address> list = addressMapper.selectList(wrapper);
        if(list == null){
            return Result.parse("您还未设置收货地址" , Result.ERROR_CODE);
        }
        return Result.parse(list , Result.SUCCESS_CODE);
    }

    /**
     * 根据前端传递的参数，新增用户的地址
     * @param address
     * @return
     */
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public HashMap<String , Object> find(@RequestBody AddressVO address){
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<Address>();
        wrapper.eq(Address::getUId , address.getUid());

        if(addressMapper.selectCount(wrapper) >= 4){
            return Result.parse("添加失败，可维护地址已超过四条" , Result.ERROR_CODE);
        }

        System.out.println(address);
        if(address == null){
            return Result.parse("添加失败，信息为空" , Result.ERROR_CODE);
        }
        Address user = new Address();
        user.setAddGender(address.getAddGender());
        user.setAddXing(address.getAddXing());
        user.setAddMing(address.getAddMing());
        user.setUId(Integer.valueOf(address.getUid()));
        user.setAddProvince(address.getAddProvince());
        user.setAddCity(address.getAddCity());
        user.setAddArea(address.getAddArea());
        user.setAddDetail(address.getAddDetail());
        user.setAddPhone(address.getAddPhone());

        addressMapper.insert(user);
        return Result.parse("添加成功" , Result.SUCCESS_CODE);
    }

    /**
     * 根据前端传递的参数，删除用户的地址
     * @param address
     * @return
     */
    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public HashMap<String , Object> delete(@RequestBody AddressVO address){
        LambdaUpdateWrapper<Address> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Address::getAddId , address.getAddid());
        addressMapper.delete(wrapper);
        return Result.parse("删除成功" , Result.SUCCESS_CODE);
    }
}
