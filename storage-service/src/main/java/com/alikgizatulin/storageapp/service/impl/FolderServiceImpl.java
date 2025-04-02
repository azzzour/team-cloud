package com.alikgizatulin.storageapp.service.impl;

import com.alikgizatulin.storageapp.dto.FolderResponse;
import com.alikgizatulin.storageapp.entity.Folder;
import com.alikgizatulin.storageapp.entity.MemberStorage;
import com.alikgizatulin.storageapp.exception.DuplicateFolderException;
import com.alikgizatulin.storageapp.exception.FileNotFoundException;
import com.alikgizatulin.storageapp.exception.FolderNotFoundException;
import com.alikgizatulin.storageapp.exception.MemberStorageNotFoundException;
import com.alikgizatulin.storageapp.repository.FolderRepository;
import com.alikgizatulin.storageapp.repository.MemberStorageRepository;
import com.alikgizatulin.storageapp.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;

    private final MemberStorageRepository memberStorageRepository;

    @Override
    public FolderResponse getById(UUID id) {
        return this.folderRepository.findById(id)
                .map(folder -> new FolderResponse(
                        folder.getId(),
                        folder.getMemberStorageId(),
                        folder.getParentFolder() != null ? folder.getParentFolder().getId() : null,
                        folder.getName(),
                        folder.getSize(),
                        folder.getCreatedAt(),
                        folder.getUpdatedAt()
                )).orElseThrow(() -> new FolderNotFoundException(id));
    }

    @Transactional
    @Override
    public void create(UUID memberStorageId, UUID parentFolderId, String name) {
        Folder parentFolder = null;
        if(parentFolderId != null) {
            parentFolder = this.folderRepository.findById(parentFolderId)
                    .orElseThrow(() -> new FolderNotFoundException(parentFolderId));
        } else {
            if(!this.memberStorageRepository.existsById(memberStorageId)) {
                throw new MemberStorageNotFoundException(memberStorageId);
            }
        }
        if(this.folderRepository.existsByMemberStorageIdAndNameAndParentFolder(memberStorageId,name,parentFolder)) {
            throw new DuplicateFolderException(memberStorageId,name,parentFolderId);
        }
        Folder folder = Folder.builder()
                .memberStorageId(memberStorageId)
                .parentFolder(parentFolder)
                .name(name)
                .build();
        this.folderRepository.save(folder);
        log.debug("Created new folder: id={}, memberId={}", folder.getId(), memberStorageId);
    }

    @Transactional
    @Override
    public void createInFolder(UUID parentFolderId, String name) {
        Folder parentFolder = this.folderRepository.findById(parentFolderId)
                .orElseThrow(() -> new FileNotFoundException(parentFolderId));
        UUID memberStorageId = parentFolder.getMemberStorageId();
        if(this.folderRepository.existsByMemberStorageIdAndNameAndParentFolder(memberStorageId,name,parentFolder)) {
            throw new DuplicateFolderException(memberStorageId,name,parentFolderId);
        }
        Folder folder = Folder.builder()
                .memberStorageId(memberStorageId)
                .parentFolder(parentFolder)
                .name(name)
                .build();
        this.folderRepository.save(folder);
        log.debug("Created new folder: id={}, memberId={},parentFolderId={}",
                folder.getId(), memberStorageId,parentFolderId);
    }

    @Transactional
    @Override
    public void createInRoot(UUID memberStorageId, String name) {
        if(this.folderRepository.existsByMemberStorageIdAndNameAndParentFolder(memberStorageId,name,null)) {
            throw new DuplicateFolderException(memberStorageId,name);
        }
        if(!this.memberStorageRepository.existsById(memberStorageId)) {
            throw new MemberStorageNotFoundException(memberStorageId);
        }
        Folder folder = Folder.builder()
                .memberStorageId(memberStorageId)
                .parentFolder(null)
                .name(name)
                .build();
        this.folderRepository.save(folder);
        log.debug("Created folder in root: id={}, memberId={}", folder.getId(), memberStorageId);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        Folder folder = this.folderRepository.findById(id)
                .orElseThrow(() -> new FolderNotFoundException(id));
        if(folder.getSize() != 0) {
            MemberStorage memberStorage = this.memberStorageRepository.findByIdAndLock(folder.getMemberStorageId())
                    .orElseThrow(() -> new MemberStorageNotFoundException(folder.getMemberStorageId()));
            Folder parentFolder = folder.getParentFolder();
            memberStorage.setUsedSize(memberStorage.getUsedSize() - folder.getSize());
            this.memberStorageRepository.save(memberStorage);
            if(parentFolder != null) {
                this.folderRepository.updateSize(parentFolder.getId(), -folder.getSize());
            }
        }
        this.folderRepository.deleteById(id);
        log.debug("Deleted folder: id={}", id);
    }
}
