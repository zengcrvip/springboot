package com.example.demo.controller;

import com.example.demo.param.NomalRequestParam;
import com.example.demo.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description: 正常接口参数校验
 * @Author: changrong.zeng
 * @Date: Created in 下午5:21 2018/11/19 .
 */
@RestController
@RequestMapping("/validation")
public class MomalValidateController {

    @Autowired
    private ValidateService validateService;

    @RequestMapping(value = "/nomal", method = RequestMethod.POST)
    public String nomal(@RequestBody @Valid NomalRequestParam nomalRequestParam){

        System.out.println(nomalRequestParam.toString());

        return "success";


    }


    @RequestMapping(value = "/special", method = RequestMethod.POST)
    public String special(@RequestBody NomalRequestParam param){
        return validateService.handle(param);
    }


}
