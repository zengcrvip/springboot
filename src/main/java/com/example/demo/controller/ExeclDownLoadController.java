package com.example.demo.controller;

import com.example.demo.execl.bo.BakHotelMgDailyAdjustDetail;
import com.example.demo.execl.download.SimpleExcelView;
import com.example.demo.execl.model.DownloadModel;
import com.example.demo.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 10:48:32$ 2019-10-16$
 **/
@RequestMapping("/temporary")
@RestController
@Slf4j
public class ExeclDownLoadController {
    public final static String DOWNLOAD_MODEL = "_downloadModel";


    @RequestMapping(value = "/climbing", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView climbing() {
        Map<String, Object> model = new HashMap<>();
        List<List<Map<String, Object>>> data = new ArrayList<>();
        List<List<String>> columnName = new ArrayList<>();

        List<String> oyoIds = Arrays.asList("CN_ANK1018", "CN_ANK1019");
//        List<BakHotelMgDailyAdjustDetail> baks = bakHotelMgDailyAdjustDetailMapper.findByCondition(oyoIds);
        List<BakHotelMgDailyAdjustDetail> baks = new ArrayList<BakHotelMgDailyAdjustDetail>();
        BakHotelMgDailyAdjustDetail detail1 =  BakHotelMgDailyAdjustDetail.builder().id(1L).oyoId("CN_ANK1018").hotelId(1234L)
                .bizDate(DateUtil.parse("yyyy-MM-dd","2019-11-01")).agreementNo("20190907059947").agreementUseVersion("V02").climbingAmount(new BigDecimal(38667)).build();

//        BakHotelMgDailyAdjustDetail detail3 =  new BakHotelMgDailyAdjustDetail();
//        detail3.setId(1L);detail3.setAgreementNo("20190907059947");detail3.setAgreementUseVersion("V02");
//        detail3.setBizDate(new Date("2019-11-01"));detail3.setClimbingAmount(new BigDecimal(38667));
//        detail3.setHotelId(1234L);detail3.setOyoId("CN_ANK1018");



        BakHotelMgDailyAdjustDetail detail2 = BakHotelMgDailyAdjustDetail.builder().id(2L).oyoId("CN_ANK1018").hotelId(1234L)
                .bizDate(DateUtil.parse("yyyy-MM-dd","2019-11-02")).agreementNo("20190907059948").agreementUseVersion("V02").climbingAmount(new BigDecimal(120)).build();
        baks.add(detail1);
        baks.add(detail2);

        List<BakHotelMgDailyAdjustDetail> result = new ArrayList<>();

        Map<Long, List<BakHotelMgDailyAdjustDetail>> collect1 = baks.stream().collect(Collectors.groupingBy(BakHotelMgDailyAdjustDetail::getHotelId));
        collect1.forEach((k,v)->{
            List<BakHotelMgDailyAdjustDetail> collect = v.stream().sorted(Comparator.comparing(BakHotelMgDailyAdjustDetail::getBizDate)).skip(1).collect(Collectors.toList());
            result.addAll(collect);
        });

        Map<String, List<BakHotelMgDailyAdjustDetail>> collect = result.stream().collect(Collectors.groupingBy(bak -> bak.getOyoId() + "#" + bak.getAgreementNo() + "#" + bak.getAgreementUseVersion()));

        List<Map<String, Object>> list = new ArrayList<>();
        collect.forEach((k, v) -> {
            HashMap map = new HashMap();
            BigDecimal reduce = v.stream().map(BakHotelMgDailyAdjustDetail::getClimbingAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            BakHotelMgDailyAdjustDetail bak = v.get(0);
            map.put("oyo_id", bak.getOyoId());
            map.put("agreement_no", bak.getAgreementNo());
            map.put("agreement_use_version", bak.getAgreementUseVersion());
            map.put("climbing_amount", reduce);
            map.put("num", v.size());
            list.add(map);
        });
        data.add(list);
        List<String> sheet1 = Arrays.asList("oyo_id", "agreement_no", "agreement_use_version", "climbing_amount", "num");
        columnName.add(sheet1);

        DownloadModel downloadModel = DownloadModel.builder()
                .templatePath("/execlTemplates/climbing_data.xlsx")
                .fileName("climbing_result" + DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",new Date()) + ".xlsx")
                .columnName(columnName)
                .data(data).build();
        model.put(DOWNLOAD_MODEL, downloadModel);
        return new ModelAndView(new SimpleExcelView(), model);
    }
}