package com.kopylov.musicplatform.exception.handler;

import com.kopylov.musicplatform.exception.AlreadyExistsException;
import com.kopylov.musicplatform.exception.NotFoundException;
import com.kopylov.musicplatform.exception.UnauthorizedException;
import com.kopylov.musicplatform.exception.data.ApiError;
import com.kopylov.musicplatform.helper.DateHelper;
import com.kopylov.musicplatform.helper.ServletPathHelper;
import com.kopylov.musicplatform.mapper.response.ResponseErrorMapper;
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

import java.io.IOException;

import static com.kopylov.musicplatform.constants.GlobalErrorDetail.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        StringBuilder message = new StringBuilder();

        message.append(e.getContentType()).append(MEDIA_TYPE_NOT_SUPPORTED);
        e.getSupportedMediaTypes().forEach(t -> message.append(t).append(", "));

        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                UNSUPPORTED_MEDIA_TYPE.value(),
                UNSUPPORTED_MEDIA_TYPE,
                message.toString(),
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @Override
    protected ResponseEntity handleHttpMessageNotReadable(
            HttpMessageNotReadableException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                BAD_REQUEST.value(),
                BAD_REQUEST,
                e.getMessage(),
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .toString();

        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                BAD_REQUEST.value(),
                BAD_REQUEST,
                message,
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @Override
    protected ResponseEntity handleMissingServletRequestParameter(
            MissingServletRequestParameterException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String message = e.getParameterName() + PARAMETER_IS_MISSING;

        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                BAD_REQUEST.value(),
                BAD_REQUEST,
                message,
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e, WebRequest request) {
        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                BAD_REQUEST.value(),
                BAD_REQUEST,
                e.getMessage(),
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(
            ConstraintViolationException e, WebRequest request) {
        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                BAD_REQUEST.value(),
                BAD_REQUEST,
                e.getMessage(),
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(
            NotFoundException e, WebRequest request) {
        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                BAD_REQUEST.value(),
                BAD_REQUEST,
                e.getMessage(),
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity handleResourceNotFoundException(
            IOException e, WebRequest request) {
        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR,
                e.getMessage(),
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity handleResourceNotFoundException(
            AlreadyExistsException e, WebRequest request) {
        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                BAD_REQUEST.value(),
                BAD_REQUEST,
                e.getMessage(),
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity handleResourceNotFoundException(
            UnauthorizedException e, WebRequest request) {
        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                UNAUTHORIZED.value(),
                UNAUTHORIZED,
                e.getMessage(),
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleResourceNotFoundException(
            RuntimeException e, WebRequest request) {
        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR,
                e.getMessage(),
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

    @Override
    protected ResponseEntity handleNoHandlerFoundException(
            NoHandlerFoundException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String message = String.format(NOT_FOUND_METHOD_FOR_URL, e.getHttpMethod(), e.getRequestURL());

        ApiError error = new ApiError(
                DateHelper.getCurrentDate(),
                BAD_REQUEST.value(),
                BAD_REQUEST,
                message,
                ServletPathHelper.getServletPath(request)
        );

        return ResponseErrorMapper.errorToEntity(error);
    }

}
