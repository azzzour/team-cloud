package com.alikgizatulin.commonlibrary.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DetailedHttpRequestException extends HttpRequestException {

    private final Object details;

    public DetailedHttpRequestException(String message, HttpStatus httpStatus, String messageKey, Object details,
                                        Object... messageArgs) {
        super(message, httpStatus, messageKey);
        this.details = details;
    }
    public DetailedHttpRequestException(HttpStatus httpStatus, String messageKey, Object details,
                                        Object... messageArgs) {
        super(httpStatus, messageKey, messageArgs);
        this.details = details;
    }
}
