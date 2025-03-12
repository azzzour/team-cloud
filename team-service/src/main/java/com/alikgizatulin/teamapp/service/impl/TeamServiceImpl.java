package com.alikgizatulin.teamapp.service.impl;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamSettings;
import com.alikgizatulin.teamapp.exception.TeamNotFoundException;
import com.alikgizatulin.teamapp.exception.TeamWithDuplicateNameException;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import com.alikgizatulin.teamapp.service.TeamService;
import com.alikgizatulin.teamapp.service.TeamSettingsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamSettingsService teamSettingsService;

    @PostAuthorize("returnObject.ownerId == authentication.name")
    @Override
    public Team getTeamById(UUID id) {
        return this.teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    @Override
    public List<Team> getTeamsByUsersId(String userId) {
        return this.teamRepository.findAllByOwnerId(userId);
    }

    @Transactional
    @Override
    public Team create(@NonNull Team team) {
        if(this.teamRepository.existsByOwnerIdAndName(team.getOwnerId(),team.getName())) {
            throw new TeamWithDuplicateNameException(team.getOwnerId(),team.getName());
        }
        Team savedTeam = this.teamRepository.saveAndFlush(team);
        log.debug("Created new team: id={}, userId={}",savedTeam.getId(),team.getOwnerId());
        return savedTeam;
    }

    @Transactional
    @Override
    public Team createWithSettings(@NonNull Team team, @NonNull TeamSettings teamSettings) {
        Team savedTeam = this.create(team);
        teamSettings.setTeam(savedTeam); //necessary?
        this.teamSettingsService.create(teamSettings);
        log.debug("Created new team with team settings: id={}, userId={}",savedTeam.getId(),team.getOwnerId());
        return savedTeam;
    }
}
