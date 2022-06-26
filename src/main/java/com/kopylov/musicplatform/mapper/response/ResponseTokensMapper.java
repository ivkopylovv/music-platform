package com.kopylov.musicplatform.mapper.response;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ResponseTokensMapper {
    private final String ACCESS_TOKEN = "access_token";
    private final String REFRESH_TOKEN = "refresh_token";

    public Map<String, String> getTokensMap(String access_token, String refresh_token) {
        Map<String, String> tokens = new HashMap<>();

        tokens.put(ACCESS_TOKEN, access_token);
        tokens.put(REFRESH_TOKEN, refresh_token);

        return tokens;
    }
}
