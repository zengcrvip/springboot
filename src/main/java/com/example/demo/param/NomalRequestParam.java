package com.example.demo.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 下午5:25 2018/11/19 .
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NomalRequestParam {

    @NotBlank(message = "appCode is required")
    private String appCode;

    @NotBlank(message = "bookingId is required")
    private String bookingId;

    @NotNull(message = "checkin is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkin;

    @NotNull(message = "checkout is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkout;

    @NotEmpty(message = "changeRoomList is required")
    @Valid
    private List<ChildRequestParam> child;
}
