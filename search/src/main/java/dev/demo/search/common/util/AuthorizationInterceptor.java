package dev.demo.search.common.util;


import dev.demo.search.common.annotation.NonAuthorized;
import dev.demo.search.common.exception.UnauthorizedException;
import dev.demo.search.common.response.ResponseCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isNonAuthorize(handler)) {
            return true;
        }
        String memberNo = request.getHeader(Constants.MEMBER_HEADER_KEY);
        if (!StringUtils.hasText(memberNo)) {
            throw new UnauthorizedException((ResponseCode.UNAUTHORIZED_REQUEST));
        }
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(memberNo));
        return true;
    }

    private boolean isNonAuthorize(Object handler) {
        NonAuthorized nonAuthorize = ((HandlerMethod) handler).getMethodAnnotation(NonAuthorized.class);
        return nonAuthorize != null;
    }

    private Authentication getAuthentication(String memberNo) {
        UserDetails userDetails = User.builder().username(memberNo).password("").roles("").build();
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}