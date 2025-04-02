package com.alikgizatulin.storageapp.service;

import com.alikgizatulin.storageapp.dto.FolderResponse;

import java.util.UUID;

public interface FolderService {
    FolderResponse getById(UUID id);
    void create(UUID memberStorageId, UUID parentFolderId, String name);
    void createInFolder(UUID parentFolderId, String name);
    void createInRoot(UUID memberStorageId, String name);
    void deleteById(UUID id);
}
