package com.kopylov.musicplatform.helper;

import lombok.experimental.UtilityClass;

import java.util.Date;

import static com.kopylov.musicplatform.constants.TokenOption.ACCESS_TOKEN_TIME_ALIVE;
import static com.kopylov.musicplatform.constants.TokenOption.REFRESH_TOKEN_TIME_ALIVE;

@UtilityClass
public class DateHelper {
    public Date getAccessTokenTimeAlive() {
        return new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME_ALIVE);
    }

    public Date getRefreshTokenTimeAlive() {
        return new Date(System.currentTimeMillis() + REFRESH_TOKEN_TIME_ALIVE);
    }

    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}
