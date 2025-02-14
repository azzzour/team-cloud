package com.alikgizatulin.commonlibrary.exception.service;

public interface ExceptionMessageSourceService {
    String getMessage(String code, Object[] args);
}
