package com.alikgizatulin.storageapp.dto;

import java.time.Instant;
import java.util.UUID;


public record TeamStorageResponse(UUID teamId,
                                  long totalSize,
                                  long reservedSize,
                                  Instant createdAt,
                                  Instant updatedAt){
}
