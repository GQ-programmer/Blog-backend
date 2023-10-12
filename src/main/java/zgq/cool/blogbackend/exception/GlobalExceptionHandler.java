package zgq.cool.blogbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zgq.cool.blogbackend.common.BaseResponse;
import zgq.cool.blogbackend.common.ErrorCode;
import zgq.cool.blogbackend.common.ResultUtils;

/**
 * @Author 孑然
 * @Date 2022 12/04 16:28
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runTimeExceptionHandler(RuntimeException e){
        log.error("runTimeException:"+e.getMessage(),e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "系统异常");
    }

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceotionHandler(BusinessException e){
        log.error("businessException:"+e.getMessage(),e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }
}
