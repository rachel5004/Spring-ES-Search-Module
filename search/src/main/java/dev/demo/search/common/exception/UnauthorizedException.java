package dev.demo.search.common.exception;

import dev.demo.search.common.response.ResponseCode;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(ResponseCode responseCode) {
        super(responseCode);
    }

}