package dev.demo.search.common.exception;

import dev.demo.search.common.response.ResponseCode;

public class NotFoundException extends BaseException {

    public NotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }

}
