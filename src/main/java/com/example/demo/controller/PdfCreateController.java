package com.example.demo.controller;

import com.example.demo.pdf.service.PdfCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Description: pdf生成入口
 * @Author: changrong.zeng
 * @Date: Created in 17:55 2018/8/17 .
 */
@RestController
public class PdfCreateController {

    @Autowired
    private PdfCreateService pdfCreateService;


    @RequestMapping("/create")
    public String create(){

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("isNewVoucher",false);
        map.put("SEQUENCEID",123455);
        map.put("ORDERID","ORDERID123456");
        map.put("PAYER_NAME","张三");
        map.put("BANKACCTID","6225768710691070");
        map.put("PAYEE_NAME","曾跑跑");
        map.put("PAYER_IDC","PAYER_IDC1234");
        map.put("strOrderAmount","伍拾圆整");
        map.put("ORDERAMOUNT","50.00");
        map.put("strOrderAmount","伍拾圆整");
        map.put("strPayerFee","叁圆伍角");
        map.put("payFee","3.50");
        map.put("strTotalAmount","伍拾圆整");
        map.put("totalAmount","50.00");
        map.put("MEMBERACCTNAME","代付");
        map.put("ORDERTYPENAME","付款到银行账户");
        map.put("ORDERTIME",new Date());
        map.put("MEMO","我是备注");
        String tempPath = "D:/springboot/tempPath";
        String basePath = "D:/springboot/basePath";
        try {
            pdfCreateService.createVoucher(map,tempPath,basePath);
        } catch (Exception e) {
            System.out.println("===========================" + e.getMessage());
            e.printStackTrace();
        }

        return "success";

    }


    @RequestMapping("/createDetail")
    public String createDetail(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id","1");
        map.put("orderId","78268382");
        map.put("payAcctId","10012215830000574");
        map.put("name","张真");
        map.put("bankName","银行名称");
        map.put("bankAcctId","62228557586755");
        map.put("bankOpenName","招商银行");
        map.put("amout","50");
        map.put("fee","3.50");
        map.put("createTime",new Date());
        map.put("endTime",new Date());
        map.put("dealType","批量付款到银行");
        map.put("orderNo","201808171819");
        map.put("errorCode","");
        map.put("errorMsg","");
        map.put("orginNo", "201808171819");
        map.put("memo", "备注");
        List<Map> list = new ArrayList<Map>();
        list.add(map);
        list.add(map);
        String tempPath = "D:/springboot/tempPath";
        String basePath = "D:/springboot/basePath";
        try {
            pdfCreateService.createBatchDetailVoucher(list,tempPath,basePath,"appCode123456",0);
        } catch (Exception e) {
            System.out.println("===========================" + e.getMessage());
            e.printStackTrace();
        }
        return "success";
    }

}
