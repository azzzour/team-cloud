package com.alikgizatulin.commonlibrary.exception.handler;

import com.alikgizatulin.commonlibrary.exception.DetailedHttpRequestException;
import com.alikgizatulin.commonlibrary.exception.HttpRequestException;
import com.alikgizatulin.commonlibrary.exception.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public interface HttpRequestExceptionHandler {

    ResponseEntity<ErrorDto> handleException(HttpRequestException ex, WebRequest request);
    ResponseEntity<ErrorDto> handleException(DetailedHttpRequestException ex, WebRequest request);
}
