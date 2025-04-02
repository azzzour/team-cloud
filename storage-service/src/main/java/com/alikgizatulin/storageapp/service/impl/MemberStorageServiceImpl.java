package com.alikgizatulin.storageapp.service.impl;

import com.alikgizatulin.storageapp.dto.MemberStorageResponse;
import com.alikgizatulin.storageapp.entity.MemberStorage;
import com.alikgizatulin.storageapp.entity.TeamStorage;
import com.alikgizatulin.storageapp.exception.DuplicateMemberStorageException;
import com.alikgizatulin.storageapp.exception.MemberStorageNotFoundException;
import com.alikgizatulin.storageapp.exception.NotEnoughTeamStorageException;
import com.alikgizatulin.storageapp.exception.TeamStorageNotFoundException;
import com.alikgizatulin.storageapp.repository.MemberStorageRepository;
import com.alikgizatulin.storageapp.repository.TeamStorageRepository;
import com.alikgizatulin.storageapp.service.MemberStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberStorageServiceImpl implements MemberStorageService {

    private final MemberStorageRepository memberStorageRepository;
    private final TeamStorageRepository teamStorageRepository;

    @Override
    public MemberStorageResponse getById(UUID id) {
        return MemberStorageResponse.fromMemberStorage(this.memberStorageRepository.findById(id)
                .orElseThrow(() -> new MemberStorageNotFoundException(id)));
    }

    @Transactional
    @Override
    public void create(UUID memberId, UUID teamStorageId, long totalSize) {
        TeamStorage teamStorage = this.teamStorageRepository.findById(teamStorageId)
                .orElseThrow(() -> new TeamStorageNotFoundException(teamStorageId));
        if(this.memberStorageRepository.existsById(memberId)){
            throw new DuplicateMemberStorageException(memberId);
        }
        long availableSize = teamStorage.getTotalSize() - teamStorage.getReservedSize();
        if(totalSize > availableSize) {
            throw new NotEnoughTeamStorageException(teamStorageId,memberId);
        }
        teamStorage.setReservedSize(teamStorage.getReservedSize() + totalSize);
        MemberStorage memberStorage = MemberStorage.builder()
                .memberId(memberId)
                .teamStorage(teamStorage)
                .totalSize(totalSize)
                .build();
        this.teamStorageRepository.save(teamStorage);
        this.memberStorageRepository.save(memberStorage);
        log.debug("Created new member storage: memberId={}, teamStorageId={}",memberId,teamStorageId);
    }
}
