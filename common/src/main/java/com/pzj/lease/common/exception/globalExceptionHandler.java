package com.pzj.lease.common.exception;

import com.pzj.lease.common.result.Result;
import com.pzj.lease.common.result.ResultCodeEnum;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class globalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(LeaseException.class)
    @ResponseBody
    public Result handle(LeaseException e){
        e.printStackTrace();
        return Result.fail(e.getCode(),e.getMessage());
    }

}
