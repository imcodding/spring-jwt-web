package com.jwt.provider;

import com.jwt.core.Role;
import com.jwt.provider.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtAuthProvider jwtAuthProvider;
    private static final String AUTHORIZATION_HEADER = "x-auth-token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("auth intercept!");

        Optional<String> token = resolveToken(request);

        if(token.isPresent()) {
            jwtAuthProvider.setToken(token.get()); //클라이언트에게 받은 토큰
            if(jwtAuthProvider.validate() & Role.USER.getCode().equals(jwtAuthProvider.getData().get("role"))) {
                log.info("jwt valid");
                return true;
            } else {
                log.info("jwt invalid");
                return false;
            }
        } else {
            log.info("jwt is not included in the header");
            return false;
        }
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        String authToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(authToken)) {
            return Optional.of(authToken);
        } else {
            return Optional.empty();
        }
    }
}
