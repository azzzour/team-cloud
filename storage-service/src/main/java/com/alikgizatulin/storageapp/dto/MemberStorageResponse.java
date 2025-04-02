package com.alikgizatulin.storageapp.dto;

import com.alikgizatulin.storageapp.entity.MemberStorage;

import java.time.Instant;
import java.util.UUID;

public record MemberStorageResponse(UUID memberId,
                                    long totalSize,
                                    long usedSize,
                                    Instant createdAt,
                                    Instant updatedAt) {

    public static MemberStorageResponse fromMemberStorage(MemberStorage memberStorage) {
        return new MemberStorageResponse(
                memberStorage.getMemberId(),
                memberStorage.getTotalSize(),
                memberStorage.getUsedSize(),
                memberStorage.getCreatedAt(),
                memberStorage.getUpdatedAt()
        );
    }

}
