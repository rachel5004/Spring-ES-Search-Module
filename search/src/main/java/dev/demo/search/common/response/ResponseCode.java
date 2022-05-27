package dev.demo.search.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    COMMON_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    UNAUTHORIZED_REQUEST("인증되지 않은 요청입니다"),
    REDIS_KEY_NOT_FOUND( "Redis에서 해당 Key를 찾을 수 없습니다."),

    // search
    INVALID_KEYWORD("유효하지 않은 검색어입니다");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
