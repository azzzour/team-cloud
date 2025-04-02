package com.alikgizatulin.storageapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService {

    void createTeamStorage(UUID teamId, long storageSize);

    void createMemberStorage(UUID teamStorageId, UUID memberId, long storageSize);

    void save(MultipartFile file, UUID teamId, UUID memberId, UUID parentFolderId);

    void createFolder(UUID memberId, UUID parentFolderId, String name);

    void deleteFolder(UUID teamId, UUID memberId, UUID folderId);

    void deleteFile(UUID teamId, UUID memberId, UUID fileId);

}
