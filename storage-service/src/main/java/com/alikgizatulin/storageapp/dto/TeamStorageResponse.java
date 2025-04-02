package com.alikgizatulin.storageapp.dto;

import com.alikgizatulin.storageapp.entity.TeamStorage;

import java.time.Instant;
import java.util.UUID;


public record TeamStorageResponse(UUID teamId,
                                  long totalSize,
                                  long reservedSize,
                                  Instant createdAt,
                                  Instant updatedAt){

    public static TeamStorageResponse fromTeamStorage(TeamStorage teamStorage) {
        return new TeamStorageResponse(
                teamStorage.getTeamId(),
                teamStorage.getTotalSize(),
                teamStorage.getReservedSize(),
                teamStorage.getCreatedAt(),
                teamStorage.getUpdatedAt()
        );
    }
}
