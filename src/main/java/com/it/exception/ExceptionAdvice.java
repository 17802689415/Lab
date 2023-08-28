package com.it.exception;

import com.it.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.it.controller")
public class ExceptionAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R handlerValidException(MethodArgumentNotValidException excetion) {
//        Map<String, String> map = new HashMap<>();
        List list = new ArrayList<>();
        BindingResult result = excetion.getBindingResult();
        result.getFieldErrors().forEach((item)->{
            String message = item.getDefaultMessage();
            String field = item.getField();
//            map.put(field, message);

            list.add("字段:"+field+"---"+"信息:"+message);
        });
        log.error("数据校验出现问题:{},异常类型{}",excetion.getMessage(),excetion.getClass());
        return R.success(list,101);
    }


}
