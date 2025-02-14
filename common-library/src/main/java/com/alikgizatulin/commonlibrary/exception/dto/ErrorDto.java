package com.alikgizatulin.commonlibrary.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Builder
@Setter
@Getter
public class ErrorDto {

    private int status;

    @JsonProperty(value = "message_key")
    private String messageKey;

    @JsonProperty(value = "message")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object details;

    private String path;

    @JsonProperty(value = "timestamp")
    private Instant timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "stack_trace")
    private String stackTrace;
}
