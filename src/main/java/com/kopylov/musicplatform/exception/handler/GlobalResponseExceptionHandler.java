package com.kopylov.musicplatform.exception.handler;

import com.kopylov.musicplatform.exception.data.ApiError;
import com.kopylov.musicplatform.mapper.ResponseErrorMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.kopylov.musicplatform.constants.GlobalErrorDetail.*;
import static com.kopylov.musicplatform.constants.GlobalErrorMessage.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        builder.append(e.getContentType()).append(MEDIA_TYPE_NOT_SUPPORTED);
        e.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        details.add(builder.toString());
        ApiError error = new ApiError(LocalDateTime.now(), INVALID_JSON, details);

        return ResponseErrorMapper.errorToEntity(error, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleHttpMessageNotReadable(
            HttpMessageNotReadableException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>(Arrays.asList(e.getMessage()));
        ApiError error = new ApiError(LocalDateTime.now(), MALFORMED_JSON_REQUEST, details);

        return ResponseErrorMapper.errorToEntity(error, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<String> details = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());
        ApiError error = new ApiError(LocalDateTime.now(), MALFORMED_JSON_REQUEST, details);

        return ResponseErrorMapper.errorToEntity(error, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleMissingServletRequestParameter(
            MissingServletRequestParameterException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();

        details.add(e.getParameterName() + PARAMETER_IS_MISSING);
        ApiError error = new ApiError(LocalDateTime.now(), MISSING_PARAMS, details);

        return ResponseErrorMapper.errorToEntity(error, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        List<String> details = new ArrayList<>(Arrays.asList(e.getMessage()));
        ApiError error = new ApiError(LocalDateTime.now(), MISMATCH_TYPE, details);

        return ResponseErrorMapper.errorToEntity(error, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(Exception e) {
        List<String> details = new ArrayList<>(Arrays.asList(e.getMessage()));
        ApiError error = new ApiError(LocalDateTime.now(), CONSTRAINT_VIOLATION, details);

        return ResponseErrorMapper.errorToEntity(error, BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleResourceNotFoundException(RuntimeException e) {
        List<String> details = new ArrayList<>(Arrays.asList(e.getMessage()));
        ApiError error = new ApiError(LocalDateTime.now(), e.getMessage(), details);

        return ResponseErrorMapper.errorToEntity(error, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleNoHandlerFoundException(
            NoHandlerFoundException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(String.format(NOT_FOUND_METHOD_FOR_URL, e.getHttpMethod(), e.getRequestURL()));
        ApiError error = new ApiError(LocalDateTime.now(), METHOD_NOT_FOUND, details);

        return ResponseErrorMapper.errorToEntity(error, BAD_REQUEST);
    }

}
