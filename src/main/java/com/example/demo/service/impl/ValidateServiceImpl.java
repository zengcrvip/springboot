package com.example.demo.service.impl;

import com.example.demo.param.NomalRequestParam;
import com.example.demo.service.ValidateService;
import com.example.demo.util.ValidationUtil;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 下午7:31 2018/11/19 .
 */
@Service
public class ValidateServiceImpl implements ValidateService {

    @Override
    public String handle(NomalRequestParam param) {
        //自定义参数校验
        ValidationUtil.commonValidate(param);
        System.out.println("service,param:" + param.toString());
        return "success";
    }

}
