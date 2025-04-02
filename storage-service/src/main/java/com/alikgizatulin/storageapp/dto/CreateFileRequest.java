package com.alikgizatulin.storageapp.dto;

import java.util.UUID;

public record CreateFileRequest(
        UUID memberStorageId,
        UUID parentFolderId,
        String name,
        long size,
        String contentType
) {
}
