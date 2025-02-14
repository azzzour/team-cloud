package com.alikgizatulin.commonlibrary.exception.handler;

import com.alikgizatulin.commonlibrary.exception.DetailedHttpRequestException;
import com.alikgizatulin.commonlibrary.exception.service.ExceptionMessageSourceService;
import com.alikgizatulin.commonlibrary.exception.HttpRequestException;
import com.alikgizatulin.commonlibrary.exception.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class HttpRequestExceptionHandlerImpl implements HttpRequestExceptionHandler {

    private final ExceptionMessageSourceService exceptionMessageSourceService;

    @Override
    public ResponseEntity<ErrorDto> handleException(HttpRequestException ex, WebRequest request) {
        ErrorDto responseBody = this.buildErrorDto(ex,request);
        this.handleRequest(ex, responseBody, request);
        return ResponseEntity.status(ex.getHttpStatus())
                .body(responseBody);
    }

    @Override
    public ResponseEntity<ErrorDto> handleException(DetailedHttpRequestException ex, WebRequest request) {
        ErrorDto responseBody = this.buildDetailedErrorDto(ex,request);
        this.handleRequest(ex, responseBody, request);
        return ResponseEntity.status(ex.getHttpStatus())
                .body(responseBody);
    }


    public ErrorDto buildErrorDto(HttpRequestException ex, WebRequest request) {
        String message = this.buildMessage(ex.getMessage(),ex.getMessageKey(),ex.getMessageArgs());
        return ErrorDto.builder()
                .status(ex.getHttpStatus().value())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .messageKey(ex.getMessageKey())
                .message(message)
                .timestamp(ex.getTimestamp())
                .build();
    }

    public ErrorDto buildDetailedErrorDto(DetailedHttpRequestException ex,WebRequest request) {
        ErrorDto result = this.buildErrorDto(ex,request);
        result.setDetails(ex.getDetails());
        return result;
    }

    private void handleRequest(HttpRequestException ex, ErrorDto errorDto,
                               WebRequest request) {
        this.logException(ex,errorDto.getMessage(),request);
    }

    private void logException(Throwable ex, String message,
                              WebRequest request) {
        String requestView = this.webRequestView(request);
        log.error("An exception occurred '{}' from request '{}' with message '{}'",ex.getClass(),requestView,message);
    }

    private String webRequestView(WebRequest request) {
        return Optional.ofNullable(request)
                .map(Object::toString)
                .orElse("exception");
    }

    private String buildMessage(String message, String messageKey, Object[] args) {
        if(message != null) {
            return message;
        }
        String result = this.exceptionMessageSourceService.getMessage(messageKey, args);
        if(result == null) {
            log.warn("Message key '{}' is missing, please add to message source", messageKey);
            return messageKey;
        }
        return result;
    }


}
