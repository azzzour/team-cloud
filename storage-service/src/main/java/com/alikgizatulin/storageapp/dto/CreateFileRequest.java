package com.alikgizatulin.storageapp.dto;

public record CreateFileRequest(
        String name,
        long size,
        String contentType
) {
}
