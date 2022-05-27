package dev.demo.search.common.exception;

import dev.demo.search.common.response.ResponseCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private final ResponseCode responseCode;

    public BaseException(ResponseCode responseCode) {
        super(responseCode.getErrorMsg());
        this.responseCode = responseCode;
    }

    public BaseException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public BaseException(String message, ResponseCode responseCode, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode;
    }
}
