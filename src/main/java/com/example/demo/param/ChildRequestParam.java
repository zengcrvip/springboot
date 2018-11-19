package com.example.demo.param;

import com.example.demo.annotations.EnumCheck;
import enums.ActionEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 下午5:27 2018/11/19 .
 */
@Data
public class ChildRequestParam {
    @NotNull(message = "roomTypeId is required")
    private Long roomTypeId;
    @NotNull(message = "rateCodeId is required")
    private Long rateCodeId;
    @NotNull(message = "numOfRooms is required")
    private Integer numOfRooms;

    @NotNull(message = "action is required")
    @EnumCheck(value = ActionEnum.class, message = "action is error")
    private String action;
}
