package com.example.demo.epcc.protocol;

import com.example.demo.epcc.model.*;
import com.example.demo.util.AmountUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 17:09 2018/9/4 .
 */
@Component("epccPaymentRequestConvertMethod")
public class EpccPaymentRequestConvertMethod extends CommonConvertMethod {

    public String getPyerAcctIssrId(EpccTxn epccTxn) {
        EpccPaymentRequestDto dto = (EpccPaymentRequestDto)epccTxn.getMessageBody();
        return BankOrgCode.getBankOrgCodeBybankid(dto.getBankId());
    }// 付款方账户所属机构标识

    public String getPyerAcctTp(EpccTxn epccTxn) {
        EpccPaymentRequestDto dto = (EpccPaymentRequestDto)epccTxn.getMessageBody();
        return AccType.getBankValueByKqValue(dto.getAccType());
    }// 付款方账户类型

    public String getSgnNo(EpccTxn epccTxn) {
        return ((EpccPaymentRequestDto)epccTxn.getMessageBody()).getSgnNo();
    }// 签约协议号

    public String getPyerTrxTrmNo(EpccTxn epccTxn) {
        return "UTF-8";
    }// 用户交易终端类型

    public String getPyeeAcctIssrId(EpccTxn epccTxn) {
        return "Z2003731000018";
    }// 收款方账户所属机构标识

    public String getPyeeAcctId(EpccTxn epccTxn) {
        return ((EpccPaymentRequestDto)epccTxn.getMessageBody()).getPyeeAcctId();
    }// 收款方账户

    public String getPyeeNm(EpccTxn epccTxn) {
        return ((EpccPaymentRequestDto)epccTxn.getMessageBody()).getPyeeNm();
    }// 收款方名称

    public String getPyeeAcctTp(EpccTxn epccTxn) {
        return ((EpccPaymentRequestDto)epccTxn.getMessageBody()).getPyeeAcctTp();
    }// 收款方账户类型

    public String getInstgAcctId(EpccTxn epccTxn) {
        EpccPaymentRequestDto dto = (EpccPaymentRequestDto)epccTxn.getMessageBody();
        return BankOrgCode.getDepositAcctBybankid(dto.getBankId());
    }// 支付机构备付金账户

    public String getInstgAcctNm(EpccTxn epccTxn) {
        EpccPaymentRequestDto dto = (EpccPaymentRequestDto)epccTxn.getMessageBody();
        return BankOrgCode.getDepositAcctInfoBybankid(dto.getBankId());
    }// 支付机构备付金账户名称

    public String getResfdAcctIssrId(EpccTxn epccTxn) {
        EpccPaymentRequestDto dto = (EpccPaymentRequestDto)epccTxn.getMessageBody();
        return BankOrgCode.getBankOrgCodeBybankid(dto.getBankId());
    }// 备付金账户所属机构标识

    public String getTrxAmt(EpccTxn epccTxn) {
        String trxAmt = ((EpccPaymentRequestDto)epccTxn.getMessageBody()).getTrxAmt();
        return "CNY" + AmountUtils.convertLiToYuan(new BigDecimal(trxAmt));
    }// 交易金额

    public String getTrxCtgy(EpccTxn epccTxn) {
        return ((EpccPaymentRequestDto)epccTxn.getMessageBody()).getTrxCtgy();
    }// 交易类别

    public String getBizTp(EpccTxn epccTxn) {
        return ((EpccPaymentRequestDto)epccTxn.getMessageBody()).getBizTp();
    }// 业务种类

    public String getOrdrId(EpccTxn epccTxn) {
        return ((EpccPaymentRequestDto)epccTxn.getMessageBody()).getOrdrId();
    }// 订单编码

    /**
     * 实现可参考bgw.protocol.ConvertMethodImpl.getOrdrDesc(TxnCtrl txnCtrl)
     * @param epccTxn
     * @return
     */
    public  String getOrdrDesc(EpccTxn epccTxn) {
        EpccPaymentRequestDto dto = (EpccPaymentRequestDto)epccTxn.getMessageBody();
        String merchantQuantity = dto.getMerchantQuantity();
        String merchantRealQuantity = dto.getMerchantRealQuantity();
        // 商户简称merchantAbbreviation;
        String merchantName = dto.getMerchantAbbreviation();
        // 商户编码merchantCode;
        String merchantCode = dto.getMerchantCode();
        // 商户类型merchantType;
        String merchantType = MerchantTp.getBankValueByKqValue(dto.getMerchantType());
        // 商户证件类型merchantIdType;
        String merchantIdType = BusinessCertifTp.getBankValueByKqValue(dto.getMerchantIdType());
        // 商户证件编码merchantIdCode;
        String merchantIdCode = dto.getMerchantIdCode();
        // 商户行业类别merchantMcc;
        String merchantMcc = dto.getMerchantMcc();
        String productQuantity = dto.getProductQuantity();
        String productRealQuantity = dto.getProductRealQuantity();
        String productAbbreviation = dto.getProductAbbreviation();
        String productAmount = dto.getProductAmount();
        productAmount = "CNY" + AmountUtils.convertLiToYuan(new BigDecimal(productAmount));
        String productCount = dto.getProductCount();
        StringBuilder commodityInfo = new StringBuilder(productQuantity+"#"+productRealQuantity+"#"+productAbbreviation+"^"+productAmount+"^"+productCount);
        // 订单信息
        StringBuilder OrdrDesc = new StringBuilder();
        OrdrDesc.append(merchantQuantity).append("|").append(merchantRealQuantity).append("|").append(merchantName).append("%")
                .append(merchantCode).append("%").append(merchantType).append("%").append(merchantIdType)
                .append("%").append(merchantIdCode).append("%").append(merchantMcc).append("%").append(commodityInfo)
                .append("|");
        return OrdrDesc.toString();
    }
    public String getPltfrmNm(EpccTxn epccTxn) {
        return ((EpccPaymentRequestDto)epccTxn.getMessageBody()).getPltfrmNm();
    }// 网络交易平台简称

}
