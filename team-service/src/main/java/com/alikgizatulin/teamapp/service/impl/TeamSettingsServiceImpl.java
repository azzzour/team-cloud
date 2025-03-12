package com.alikgizatulin.teamapp.service.impl;

import com.alikgizatulin.teamapp.entity.TeamSettings;
import com.alikgizatulin.teamapp.exception.DuplicateTeamSettingsException;
import com.alikgizatulin.teamapp.exception.TeamSettingsNotFoundException;
import com.alikgizatulin.teamapp.repository.TeamSettingsRepository;
import com.alikgizatulin.teamapp.service.TeamSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class TeamSettingsServiceImpl implements TeamSettingsService {

    private final TeamSettingsRepository teamSettingsRepository;

    @Override
    public TeamSettings getById(UUID id) {
        return this.teamSettingsRepository.findById(id)
                .orElseThrow(() -> new TeamSettingsNotFoundException(id));
    }

    @Transactional
    @Override
    public TeamSettings create(TeamSettings teamSettings) {
        log.debug("Starting createTeamSettings for id={}", teamSettings.getId());
        UUID id = teamSettings.getTeam().getId();
        if(this.teamSettingsRepository.existsById(id)) {
            throw new DuplicateTeamSettingsException(id);
        }
        TeamSettings savedTeamSettings = this.teamSettingsRepository.save(teamSettings);
        log.debug("Created new team settings: id={}",savedTeamSettings.getId());
        return savedTeamSettings;
    }
}
