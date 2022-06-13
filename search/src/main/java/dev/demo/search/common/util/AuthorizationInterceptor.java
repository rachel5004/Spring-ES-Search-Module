package dev.demo.search.common.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.demo.search.common.annotation.Authorized;
import dev.demo.search.common.annotation.NonAuthorized;
import dev.demo.search.common.exception.UnauthorizedException;
import dev.demo.search.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final ObjectMapper mapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {

        log.info(String.format("[preHandler] %s", request.getRequestURI()));
        if (isNonAuthorize(handler) || request.getRequestURI().equals(Constants.HEALTH_CHECK)) {
            return true;
        }
        String token = request.getHeader(Constants.MEMBER_HEADER_KEY);
        if (isAuthorize(handler) && !StringUtils.hasText(token)) {
            throw new UnauthorizedException((ResponseCode.UNAUTHORIZED_REQUEST));
        }
        if (StringUtils.hasText(token)) {
            AuthContents authContents = mapper.readValue(getPayload(token), AuthContents.class);
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(authContents.getMember_no()));
            log.info(String.format("[HEADER] Member No: %s", authContents.getMember_no()));
        }
        return true;
    }

    private boolean isNonAuthorize(Object handler) {
        NonAuthorized nonAuthorize = ((HandlerMethod) handler).getMethodAnnotation(NonAuthorized.class);
        return nonAuthorize != null;
    }

    private boolean isAuthorize(Object handler) {
        Authorized authorized = ((HandlerMethod) handler).getMethodAnnotation(Authorized.class);
        return authorized != null;
    }

    private String getPayload(String token) {
        token = token.replace(Constants.TOKEN_PREFIX,"");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(token.split("\\.")[1]));
    }

    private Authentication getAuthentication(String memberNo) {
        UserDetails userDetails = User.builder().username(memberNo).password("").roles("").build();
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}