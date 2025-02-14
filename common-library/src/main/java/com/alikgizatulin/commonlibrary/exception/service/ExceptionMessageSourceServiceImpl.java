package com.alikgizatulin.commonlibrary.exception.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ExceptionMessageSourceServiceImpl implements ExceptionMessageSourceService {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String code, Object[] args) {
        final Locale locale = LocaleContextHolder.getLocale();
        return this.messageSource.getMessage(code, args, null, locale);
    }
}
