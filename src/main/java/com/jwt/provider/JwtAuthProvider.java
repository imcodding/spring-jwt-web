package com.jwt.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Component
public class JwtAuthProvider {

    //클라이언트가 전달받은 토큰이라고 가정
    public static String CLIENT_TOKEN = "";
    @Value("${JWT.SECRET}")
    private String secretKey;
    private Key key;
    @Setter
    private String token;


    //토큰 만료시간 1분
    private final static long tokenExpiredTime = 60;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Optional<String> createJwtAuthToken(String email) {
        final ZonedDateTime now = ZonedDateTime.now();
        var token = Jwts.builder()
                .setSubject("access_token")
                .claim("email", email)
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(now.toInstant().plusSeconds(tokenExpiredTime)))
                .compact();

        return Optional.ofNullable(token);
    }

    public boolean validate() {
        return getData() != null;
    }

    public Claims getData() {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
            throw new SecurityException();
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            throw new MalformedJwtException("invalid");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new ExpiredJwtException(null, null, "expired");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            throw new UnsupportedJwtException("unsupported");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            throw new IllegalArgumentException();
        }
    }
}
