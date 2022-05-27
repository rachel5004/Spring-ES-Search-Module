package dev.demo.search.common.exception;

import dev.demo.search.common.exception.BaseException;
import dev.demo.search.common.response.CommonResponse;
import dev.demo.search.common.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static final List<ResponseCode> ERROR_CODE_LIST = new ArrayList<>();

    // 정의되지 않은 에러
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResponse onException(Exception exception) {
        printLog(exception.getClass().getName(), exception.getMessage());
        return CommonResponse.fail(ResponseCode.COMMON_ERROR);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = UnauthorizedException.class)
    public CommonResponse onUnauthorizedException(UnauthorizedException exception) {
        printLog(exception);
        return CommonResponse.fail(ResponseCode.UNAUTHORIZED_REQUEST);
    }


    private void printLog(BaseException exception){
        log.error(String.format("[Error] %s: %s", exception.getClass().getName(), exception.getResponseCode().getErrorMsg()));
    }

    private void printLog(String exceptionName, String message) {
        log.error(String.format("[Error] %s: %s", exceptionName, message));
    }

}
