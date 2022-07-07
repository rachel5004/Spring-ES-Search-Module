package dev.demo.search.common.util;

import dev.demo.search.common.exception.UnauthorizedException;
import dev.demo.search.common.response.ResponseCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static String getCurrentMemberNo() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UnauthorizedException(ResponseCode.UNAUTHORIZED_REQUEST);
        }

        return authentication.getName().equals(Constants.ANONYMOUS) ? null : authentication.getName();
    }

}