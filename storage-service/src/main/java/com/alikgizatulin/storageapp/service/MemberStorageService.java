package com.alikgizatulin.storageapp.service;


import com.alikgizatulin.storageapp.dto.MemberStorageResponse;

import java.util.UUID;

public interface MemberStorageService {
    MemberStorageResponse getById(UUID id);
    void create(UUID memberId, UUID teamStorageId, long totalSize);
}
