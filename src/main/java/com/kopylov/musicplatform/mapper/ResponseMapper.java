package com.kopylov.musicplatform.mapper;

import com.kopylov.musicplatform.exception.data.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {
    public static ResponseEntity errorToEntity(ResponseError error, HttpStatus status) {
        return new ResponseEntity(error, status);
    }
}
