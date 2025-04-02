package com.alikgizatulin.storageapp.dto;

import java.time.Instant;
import java.util.UUID;

public record MemberStorageResponse(UUID memberId,
                                    UUID teamStorageId,
                                    long totalSize,
                                    long usedSize,
                                    Instant createdAt,
                                    Instant updatedAt) {

}
