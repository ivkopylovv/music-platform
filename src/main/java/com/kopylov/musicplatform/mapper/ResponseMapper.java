package com.kopylov.musicplatform.mapper;

import com.kopylov.musicplatform.exception.data.ApiError;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {
    public static ResponseEntity errorToEntity(ApiError error) {
        return new ResponseEntity(error, error.getStatus());
    }
}
