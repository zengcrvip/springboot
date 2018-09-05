package com.example.demo;

import com.example.demo.epcc.model.EpccPaymentRequestDto;
import com.example.demo.epcc.service.EpccSingleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 16:01 2018/9/4 .
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EpccSingleServiceTests {

    @Autowired
    private EpccSingleService epccSingleService;

    @Test
    public void EpccSingleServiceTest(){
        EpccPaymentRequestDto paymentRequestVo = new EpccPaymentRequestDto();
        paymentRequestVo.setSgnNo("28c6e9fcbb364b75");
        paymentRequestVo.setBankId("TEST");
        paymentRequestVo.setPyeeAcctId("test@163.com");
        paymentRequestVo.setPyeeNm("测试商户");
        paymentRequestVo.setPyeeAcctTp("04");
        paymentRequestVo.setAccType("0201");
        paymentRequestVo.setTrxAmt("17000");
        paymentRequestVo.setTrxCtgy("0110");
        paymentRequestVo.setBizTp("100004");
        paymentRequestVo.setOrdrId("" + (System.currentTimeMillis()));
        paymentRequestVo.setMerchantAbbreviation("测试商户");
        paymentRequestVo.setMerchantCode("105000047220001");
        paymentRequestVo.setMerchantIdCode("111111222222");
        paymentRequestVo.setMerchantIdType("104");
        paymentRequestVo.setMerchantMcc("4511");
        paymentRequestVo.setMerchantType("03");
        paymentRequestVo.setPltfrmNm("测试商户");
        paymentRequestVo.setMerchantQuantity("1");
        paymentRequestVo.setMerchantRealQuantity("1");
        paymentRequestVo.setProductAbbreviation("网购商品");
        paymentRequestVo.setProductAmount("17000");
        paymentRequestVo.setProductCount("1");
        paymentRequestVo.setProductQuantity("1");
        paymentRequestVo.setProductRealQuantity("1");
        epccSingleService.doDebitSingle(paymentRequestVo);

    }
}
