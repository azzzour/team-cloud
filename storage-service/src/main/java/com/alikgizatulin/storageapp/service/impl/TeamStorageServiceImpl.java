package com.alikgizatulin.storageapp.service.impl;

import com.alikgizatulin.storageapp.dto.TeamStorageResponse;
import com.alikgizatulin.storageapp.entity.TeamStorage;
import com.alikgizatulin.storageapp.exception.DuplicateTeamStorageException;
import com.alikgizatulin.storageapp.exception.TeamStorageNotFoundException;
import com.alikgizatulin.storageapp.repository.TeamStorageRepository;
import com.alikgizatulin.storageapp.service.TeamStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TeamStorageServiceImpl implements TeamStorageService {

    private final TeamStorageRepository teamStorageRepository;

    @Override
    public TeamStorageResponse getById(UUID id) {
        return TeamStorageResponse.fromTeamStorage(this.teamStorageRepository.findById(id)
                .orElseThrow(() -> new TeamStorageNotFoundException(id)));
    }

    @Transactional
    @Override
    public void create(UUID teamId, long totalSize, long reservedSize) {
        if(this.teamStorageRepository.existsById(teamId)) {
            throw new DuplicateTeamStorageException(teamId);
        }
        TeamStorage teamStorage = TeamStorage.builder()
                .teamId(teamId)
                .totalSize(totalSize)
                .reservedSize(reservedSize)
                .build();
        this.teamStorageRepository.saveAndFlush(teamStorage);
        log.debug("Created new team storage: teamId={}", teamId);
    }

    @Transactional
    @Override
    public void update(UUID id, Long totalSize, Long reservedSize) {
        TeamStorage teamStorage = this.teamStorageRepository.findById(id)
                .orElseThrow(() -> new TeamStorageNotFoundException(id));
        if(totalSize != null) {
            teamStorage.setTotalSize(totalSize);
        }
        if(reservedSize != null) {
            teamStorage.setReservedSize(reservedSize);
        }
        this.teamStorageRepository.save(teamStorage);
        log.debug("Updated team storage: teamId={}", id);
    }

}
