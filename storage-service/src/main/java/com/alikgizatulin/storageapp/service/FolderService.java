package com.alikgizatulin.storageapp.service;

import java.util.UUID;

public interface FolderService {
    void create(UUID memberStorageId, UUID parentFolderId, String name);

    void deleteById(UUID id);
}
