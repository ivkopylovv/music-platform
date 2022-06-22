package com.kopylov.musicplatform.exception.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.kopylov.musicplatform.constants.DateFormat.API_ERROR_FORMAT;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {

    @JsonFormat(shape = STRING, pattern = API_ERROR_FORMAT)
    private LocalDateTime timestamp;

    private String message;
    private List<String> errors;
}
