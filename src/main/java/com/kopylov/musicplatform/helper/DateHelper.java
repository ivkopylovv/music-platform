package com.kopylov.musicplatform.helper;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class DateHelper {
    public Date getTokenTimeAlive(Long tokenTimeAlive) {
        return new Date(System.currentTimeMillis() + tokenTimeAlive);
    }

    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}
