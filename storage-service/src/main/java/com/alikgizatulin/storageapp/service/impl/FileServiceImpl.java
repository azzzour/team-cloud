package com.alikgizatulin.storageapp.service.impl;

import com.alikgizatulin.storageapp.dto.CreateFileRequest;
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

    @Transactional
    @Override
    public void create(CreateFileRequest request) {
        Folder parentFolder = Objects.isNull(request.parentFolderId())
                ? null
                : folderRepository.findById(request.parentFolderId())
                .orElseThrow(() -> new FolderNotFoundException(request.parentFolderId()));

        if (this.fileRepository
                .existsByMemberStorageIdAndNameAndFolder(request.memberStorageId(), request.name(), parentFolder)) {
            throw new DuplicateFileException(request.memberStorageId(), request.name(), request.parentFolderId());
        }


        if(request.size() != 0) {
            MemberStorage memberStorage = this.memberStorageRepository.findByIdAndLock(request.memberStorageId())
                    .orElseThrow(() -> new MemberStorageNotFoundException(request.memberStorageId()));
            long availableSize = memberStorage.getTotalSize() - memberStorage.getUsedSize();
            if(request.size() > availableSize) {
                throw new NotEnoughMemberStorageException(memberStorage.getMemberId());
            }
            if(parentFolder != null) {
                this.folderRepository.updateSize(request.parentFolderId(), request.size());
            }
            memberStorage.setUsedSize(memberStorage.getUsedSize() + request.size());
            this.memberStorageRepository.save(memberStorage);
        } else if(Objects.isNull(parentFolder)) {
            if(!this.memberStorageRepository.existsById(request.memberStorageId())) {
                throw new MemberStorageNotFoundException(request.memberStorageId());
            }
        }

        File file = File.builder()
                .memberStorageId(request.memberStorageId())
                .name(request.name())
                .size(request.size())
                .folder(parentFolder)
                .contentType(request.contentType())
                .build();
        this.fileRepository.save(file);
        log.debug("Created new file: id={}, memberId={}",file.getId(),request.memberStorageId());
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
