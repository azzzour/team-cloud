package com.alikgizatulin.storageapp.service.impl;

import com.alikgizatulin.storageapp.dto.CreateFileRequest;
import com.alikgizatulin.storageapp.dto.MemberStorageResponse;
import com.alikgizatulin.storageapp.exception.StorageException;
import com.alikgizatulin.storageapp.repository.FileRepository;
import com.alikgizatulin.storageapp.repository.FolderRepository;
import com.alikgizatulin.storageapp.service.*;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;
    private final TeamStorageService teamStorageService;
    private final FileService fileService;
    private final FolderService folderService;
    private final MemberStorageService memberStorageService;
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;


    @Transactional
    @Override
    public void createTeamStorage(UUID teamId, long storageSize) {
        this.teamStorageService.create(teamId, storageSize, 0);
        String bucket = teamId.toString();
        try {
            this.minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucket)
                            .build());
            log.debug("Created new bucket={}", bucket);
        } catch (Exception e) {
            throw new StorageException
                    (String.format("Error creating bucket=%s, message=%s", bucket,e.getMessage()));
        }
    }

    @Transactional
    @Override
    public void createMemberStorage(UUID teamStorageId, UUID memberId, long storageSize) {
        this.memberStorageService.create(memberId, teamStorageId, storageSize);
    }

    @Transactional
    @Override
    public void save(MultipartFile file, UUID memberId, UUID parentFolderId) {
        var createFileRequest = new CreateFileRequest(
                file.getOriginalFilename(),
                file.getSize(),
                file.getContentType()
        );

        if(Objects.isNull(parentFolderId)) {
            this.fileService.createInRoot(memberId,createFileRequest);
        } else {
            this.fileService.createInFolder(parentFolderId,createFileRequest);
        }

        MemberStorageResponse memberStorage = this.memberStorageService.getById(memberId);
        String bucket = memberStorage.teamStorageId().toString();
        String pathToSave = memberId + "/";
        if (parentFolderId != null) {
            pathToSave += this.folderRepository.getPath(parentFolderId) + "/";
        }
        pathToSave += file.getOriginalFilename();
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(pathToSave)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            log.debug("Created new object: bucket={}, path={}", bucket, pathToSave);
        } catch (Exception e) {
            throw new StorageException(
                    String.format("Error save file in bucket =%s, path=%s message=%s",
                            bucket,pathToSave, e.getMessage())
            );
        }

    }

    @Override
    public void createFolder(UUID memberId, UUID parentFolderId, String name) {
        if(Objects.isNull(parentFolderId)) {
            this.folderService.createInRoot(memberId,name);
        } else {
            this.folderService.createInFolder(parentFolderId,name);
        }
    }

    @Transactional
    @Override
    public void deleteFolder(UUID folderId) {
        var folder = this.folderService.getById(folderId);
        String pathToDelete = this.folderRepository.getPath(folderId);
        this.folderService.deleteById(folderId);
        var memberStorage = this.memberStorageService.getById(folder.memberStorageId());
        pathToDelete =  memberStorage.memberId() + "/" + pathToDelete;
        String bucket = memberStorage.teamStorageId().toString();
        try {
            Iterable<Result<Item>> objects = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucket)
                            .prefix(pathToDelete)
                            .recursive(true)
                            .build()
            );
            List<DeleteObject> deleteObjects = new ArrayList<>();
            for (Result<Item> item : objects) {
                deleteObjects.add(new DeleteObject(item.get().objectName()));
            }
            if (!deleteObjects.isEmpty()) {
                Iterable<Result<DeleteError>> deleteResults = minioClient.removeObjects(
                        RemoveObjectsArgs.builder()
                                .bucket(bucket)
                                .objects(deleteObjects)
                                .build()
                );
                for (Result<DeleteError> result : deleteResults) {
                    DeleteError error = result.get();
                    log.error("Error in deleting object " + error.objectName() + "; " + error.message());
                }
            }

        } catch (Exception e) {
            throw new StorageException(
                    String.format("Error delete folder in bucket =%s, path =%s, message=%s",
                            bucket,pathToDelete,e.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public void deleteFile(UUID fileId) {
        var file = this.fileService.getById(fileId);
        String pathToDelete = this.fileRepository.getPath(fileId);
        this.fileService.deleteById(fileId);
        var memberStorage = this.memberStorageService.getById(file.memberStorageId());
        pathToDelete = memberStorage.memberId() + "/" + pathToDelete;
        String bucket = memberStorage.teamStorageId().toString();
        try {
            this.minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(pathToDelete)
                            .build());
            log.debug("Deleted object: bucket={}, path={}", bucket, pathToDelete);
        } catch (Exception e) {
            throw new StorageException(
                    String.format("Error delete object in bucket =%s, path =%s, message=%s",
                    bucket,pathToDelete,e.getMessage())
            );
        }
    }
}
