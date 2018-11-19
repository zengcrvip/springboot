package com.example.demo.controller.advice;

import com.example.demo.exception.ServiceException;
import enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 1.根据BaseResponse的code设置对应的HttpStatus,使响应信息更清晰 </br>
 * 2.业务外的异常统一返回体,如RequestBody装配错误,请求方式不对,404 NOT FOUND等
 *
 * @author Swift Hu
 * @date 2018年8月29日 下午1:59:42
 */
@Slf4j
@ControllerAdvice
@RestController
public class ExceptionAndHttpStatusHandlerAdvice implements ResponseBodyAdvice<BaseResponse<Object>>, ErrorController {
    private static final String PATH = "/error";

    /**
     * 统一参数验证异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> validExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("The request [{}] BindException: {}", request.getRequestURI(), e.getMessage());
        return BaseResponse.fail("9999",
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 统一ServiceException异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServiceException.class)
    public BaseResponse<String> serviceException(HttpServletRequest request, ServiceException e) {
        log.warn("The request [{}] ServiceException: {}", request.getRequestURI(), e.getMsg());
        return BaseResponse.fail(e.getCode(), e.getMsg());
    }

    /**
     * 统一错误返回体
     *
     * @param request
     * @param e
     * @return
     * @author Swift Hu
     * @date 2018年8月29日 下午1:55:55
     */
    @RequestMapping(value = PATH)
    @ExceptionHandler(value = Exception.class)
    public BaseResponse<String> error(HttpServletRequest request, Exception e) {
        if (e instanceof ServiceException) {
            return BaseResponse.fail(((ServiceException) e).getCode(), ((ServiceException) e).getMsg());
        }

        long errorCode = System.currentTimeMillis();
        log.error("The request [{}] is error, errorCode: {}", request.getRequestURI(), errorCode, e);
        return BaseResponse.fail(ResponseCode.FAILURE.getCode(), String.format("未知错误, 错误码:%s , 错误信息: %s", errorCode, ResponseCode.SYSTEM_IS_BUSY.getMsg()));
    }

    @Override
    public BaseResponse<Object> beforeBodyWrite(BaseResponse<Object> br, MethodParameter var2, MediaType var3,
                                                Class<? extends HttpMessageConverter<?>> var4, ServerHttpRequest req, ServerHttpResponse res) {
        // 根据BaseResponse里的code设置对应的HttpStatus code
        if (br.getCode().equals("400")) {
            res.setStatusCode(HttpStatus.BAD_REQUEST);
        }
        if (br.getCode().equals("500")) {
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return br;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> var2) {
        // 处理所有返回值为BaseResponse类型的controller
        return returnType.getMethod().getReturnType().isAssignableFrom(BaseResponse.class);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
