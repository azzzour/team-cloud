package com.alikgizatulin.storageapp.service;

import com.alikgizatulin.storageapp.dto.CreateFileRequest;
import com.alikgizatulin.storageapp.dto.FileResponse;

import java.util.UUID;

public interface FileService {
    FileResponse getById(UUID id);
    void createInRoot(UUID memberStorageId,CreateFileRequest request);
    void createInFolder(UUID parentFolderId,CreateFileRequest request);
    void deleteById(UUID id);
}
