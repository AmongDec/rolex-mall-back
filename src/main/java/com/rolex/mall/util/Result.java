package com.rolex.mall.util;

import java.util.HashMap;
//工具类
public class Result {

    public static final int ERROR_CODE = 0;
    public static final int SUCCESS_CODE = 1;
    public static final int EXCEPTION_CODE = -1;

    public static HashMap<String , Object> parse(Object data , int code){
        HashMap<String , Object> result = new HashMap<>();
        result.put("mydata" , data);
        if(code == 0){
            result.put("message" , "error");
        }else if(code == 1){
            result.put("message" , "success");
        }else{
            result.put("message" , "exception");
        }
        return result;
    }
}
