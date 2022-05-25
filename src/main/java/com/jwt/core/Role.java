package com.jwt.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("ADMIN", "관리자 권한"),
    USER("USER", "사용자 권한"),
    UNKNOWN("UNKNOWN", "알 수 없는 권한");

    private final String code;
    private final String description;

    public static Role of(String code) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(UNKNOWN);
    }
}
