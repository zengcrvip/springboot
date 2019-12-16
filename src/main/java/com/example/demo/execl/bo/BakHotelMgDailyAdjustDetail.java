package com.example.demo.execl.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BakHotelMgDailyAdjustDetail {
    private Long id;


    private String oyoId;

    private Long hotelId;


    private Date bizDate;


    private String agreementNo;


    private String agreementUseVersion;


    private BigDecimal climbingAmount;


}