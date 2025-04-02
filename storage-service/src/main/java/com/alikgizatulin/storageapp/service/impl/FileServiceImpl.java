package com.alikgizatulin.storageapp.service.impl;

import com.alikgizatulin.storageapp.dto.CreateFileRequest;
import com.alikgizatulin.storageapp.dto.FileResponse;
import com.alikgizatulin.storageapp.entity.File;
import com.alikgizatulin.storageapp.entity.Folder;
import com.alikgizatulin.storageapp.entity.MemberStorage;
import com.alikgizatulin.storageapp.exception.*;
import com.alikgizatulin.storageapp.repository.FileRepository;
import com.alikgizatulin.storageapp.repository.FolderRepository;
import com.alikgizatulin.storageapp.repository.MemberStorageRepository;
import com.alikgizatulin.storageapp.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final MemberStorageRepository memberStorageRepository;
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;

    @Override
    public FileResponse getById(UUID id) {
        return this.fileRepository.findById(id)
                .map(file -> new FileResponse(
                        file.getId(),
                        file.getMemberStorageId(),
                        Objects.isNull(file.getFolder()) ? null : file.getFolder().getId(),
                        file.getName(),
                        file.getSize(),
                        file.getContentType(),
                        file.getCreatedAt(),
                        file.getUpdatedAt()
                )).orElseThrow(() -> new FileNotFoundException(id));
    }

    @Transactional
    @Override
    public void createInRoot(UUID memberStorageId, CreateFileRequest request) {
        if (this.fileRepository
                .existsByMemberStorageIdAndNameAndFolder(memberStorageId, request.name(), null)) {
            throw new DuplicateFileException(memberStorageId, request.name());
        }
        MemberStorage memberStorage = (request.size() != 0)
                ? this.memberStorageRepository.findByIdAndLock(memberStorageId)
                .orElseThrow(() -> new MemberStorageNotFoundException(memberStorageId))
                : this.memberStorageRepository.findById(memberStorageId)
                .orElseThrow(() -> new MemberStorageNotFoundException(memberStorageId));
        if(request.size() != 0) {
            long availableSize = memberStorage.getTotalSize() - memberStorage.getUsedSize();
            if(request.size() > availableSize) {
                throw new NotEnoughMemberStorageException(memberStorage.getMemberId());
            }
            memberStorage.setUsedSize(memberStorage.getUsedSize() + request.size());
            this.memberStorageRepository.save(memberStorage);
        }
        File file = File.builder()
                .memberStorageId(memberStorageId)
                .name(request.name())
                .size(request.size())
                .folder(null)
                .contentType(request.contentType())
                .build();
        this.fileRepository.save(file);
        log.debug("Created new file in root: id={}, memberId={}",file.getId(),memberStorage);
    }

    @Transactional
    @Override
    public void createInFolder(UUID parentFolderId, CreateFileRequest request) {
        Folder parentFolder = folderRepository.findById(parentFolderId)
                .orElseThrow(() -> new FolderNotFoundException(parentFolderId));
        UUID memberStorageId = parentFolder.getMemberStorageId();
        if (this.fileRepository
                .existsByMemberStorageIdAndNameAndFolder(memberStorageId, request.name(), parentFolder)) {
            throw new DuplicateFileException(memberStorageId, request.name(), parentFolderId);
        }
        MemberStorage memberStorage = (request.size() != 0)
                ? this.memberStorageRepository.findByIdAndLock(memberStorageId)
                .orElseThrow(() -> new MemberStorageNotFoundException(memberStorageId))
                : this.memberStorageRepository.findById(memberStorageId)
                .orElseThrow(() -> new MemberStorageNotFoundException(memberStorageId));
        if(request.size() != 0) {
            long availableSize = memberStorage.getTotalSize() - memberStorage.getUsedSize();
            if(request.size() > availableSize) {
                throw new NotEnoughMemberStorageException(memberStorage.getMemberId());
            }
            this.folderRepository.updateSize(parentFolderId, request.size());
            memberStorage.setUsedSize(memberStorage.getUsedSize() + request.size());
            this.memberStorageRepository.save(memberStorage);
        }
        File file = File.builder()
                .memberStorageId(memberStorageId)
                .name(request.name())
                .size(request.size())
                .folder(parentFolder)
                .contentType(request.contentType())
                .build();
        this.fileRepository.save(file);
        log.debug("Created new file: id={}, memberId={}, folder={}",file.getId(),memberStorage,parentFolderId);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        File file = this.fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));
        if(file.getSize() != 0) {
            MemberStorage memberStorage = this.memberStorageRepository.findByIdAndLock(file.getMemberStorageId())
                    .orElseThrow(() -> new MemberStorageNotFoundException(file.getMemberStorageId()));
            Folder parentFolder = file.getFolder();
            memberStorage.setUsedSize(memberStorage.getUsedSize() - file.getSize());
            this.memberStorageRepository.save(memberStorage);
            if(parentFolder != null) {
                this.folderRepository.updateSize(parentFolder.getId(), -file.getSize());
            }
        }
        this.fileRepository.deleteById(file.getId());
        log.debug("Deleted file: id={}", id);
    }
}
