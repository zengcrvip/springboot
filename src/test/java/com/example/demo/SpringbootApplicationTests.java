package com.example.demo;

import com.example.demo.pdf.service.PdfCreateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Autowired
	private PdfCreateService pdfCreateService;

	@Test
	public void contextLoads() {
		String str = "172800000";

		String aa = "172800000";
		if(!StringUtils.isEmpty(aa)){
			long num = Long.parseLong(aa);
			System.out.println("=================================num:" + num);
		}else{
			System.out.println("++++++++++++++++++");
		}
	}

	@Test
	public void pdfCreate(){
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

	}



}
