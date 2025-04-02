package com.alikgizatulin.storageapp.dto;

import java.time.Instant;
import java.util.UUID;

public record FileResponse(UUID id,
                           UUID memberStorageId,
                           UUID folderId,
                           String name,
                           long size,
                           String contentType,
                           Instant createdAt,
                           Instant updatedAt) {
}
