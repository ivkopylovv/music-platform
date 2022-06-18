package com.kopylov.musicplatform.mapper;

import com.kopylov.musicplatform.exception.data.ApiError;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseMapper {
    public ResponseEntity errorToEntity(ApiError error) {
        return new ResponseEntity(error, error.getStatus());
    }
}
