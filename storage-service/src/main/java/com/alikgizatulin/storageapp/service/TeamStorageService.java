package com.alikgizatulin.storageapp.service;

import com.alikgizatulin.storageapp.dto.TeamStorageResponse;

import java.util.UUID;

public interface TeamStorageService {
    TeamStorageResponse getById(UUID id);
    void create(UUID teamId, long totalSize, long reservedSize);
    void update(UUID id, Long totalSize, Long reservedSize);
}
