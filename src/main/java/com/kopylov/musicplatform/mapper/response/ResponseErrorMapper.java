package com.kopylov.musicplatform.mapper.response;

import com.kopylov.musicplatform.exception.data.ApiError;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseErrorMapper {

    public ResponseEntity errorToEntity(ApiError error, HttpStatus status) {
        return new ResponseEntity(error, status);
    }
}
