package com.alikgizatulin.commonlibrary.exception.handler;

import com.alikgizatulin.commonlibrary.exception.DetailedHttpRequestException;
import com.alikgizatulin.commonlibrary.exception.HttpRequestException;
import com.alikgizatulin.commonlibrary.exception.dto.ErrorDto;
import com.alikgizatulin.commonlibrary.exception.dto.ValidationErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static com.alikgizatulin.commonlibrary.exception.MessageCode.INVALID_REQUEST_ERROR;
import static com.alikgizatulin.commonlibrary.exception.MessageCode.UNKNOWN_ERROR;

@RestControllerAdvice()
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpRequestExceptionHandler httpRequestExceptionHandler;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(WebRequest request, Exception ex) {
        HttpRequestException newEx = new HttpRequestException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                UNKNOWN_ERROR,
                ex);
        return this.httpRequestExceptionHandler.handleException(newEx, request);
    }

    @ExceptionHandler(HttpRequestException.class)
    public ResponseEntity<ErrorDto> handleHttpRequestException(HttpRequestException ex,
                                                               WebRequest request) {
        return this.httpRequestExceptionHandler.handleException(ex, request);
    }

    @ExceptionHandler(DetailedHttpRequestException.class)
    public ResponseEntity<ErrorDto> handleHttpRequestExceptionWithDetails(DetailedHttpRequestException ex,
                                                                          WebRequest request) {
        return this.httpRequestExceptionHandler.handleException(ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidateException(MethodArgumentNotValidException ex,
                                                                          WebRequest request) {
        return this.httpRequestExceptionHandler.handleException(this.toDetailedHttpException(ex), request);
    }

    private DetailedHttpRequestException toDetailedHttpException(MethodArgumentNotValidException ex) {
        return new DetailedHttpRequestException(
                HttpStatus.BAD_REQUEST,
                INVALID_REQUEST_ERROR,
                this.extractFieldValidationErrors(ex)
                );
    }

    private List<ValidationErrorDto> extractFieldValidationErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .map(this::toValidationErrorDto)
                .toList();
    }

    private ValidationErrorDto toValidationErrorDto(FieldError fieldError) {
        return ValidationErrorDto.builder()
                .field(fieldError.getField())
                .rejectedValue(fieldError.getRejectedValue())
                .defaultMessage(fieldError.getDefaultMessage())
                .build();
    }

}
