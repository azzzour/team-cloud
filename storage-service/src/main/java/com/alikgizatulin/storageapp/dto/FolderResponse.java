package com.alikgizatulin.storageapp.dto;

import java.time.Instant;
import java.util.UUID;

public record FolderResponse(UUID id,
                             UUID memberStorageId,
                             UUID parentFolderId,
                             String name,
                             long size,
                             Instant createdAt,
                             Instant updatedAt
                             ) {
}
