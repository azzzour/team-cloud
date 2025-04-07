package com.alikgizatulin.teamapp.service.impl;

import com.alikgizatulin.teamapp.dto.CreateTeamRequest;
import com.alikgizatulin.teamapp.dto.TeamResponse;
import com.alikgizatulin.teamapp.dto.UpdateTeamRequest;
import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamMember;
import com.alikgizatulin.teamapp.exception.TeamNotFoundException;
import com.alikgizatulin.teamapp.exception.TeamWithDuplicateNameException;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import com.alikgizatulin.teamapp.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public Page<TeamResponse> getUserTeams(String userId, String name, Pageable pageable) {
        Page<Team> teams = this.teamRepository.findAllUserTeams(userId, name, pageable);
        return teams.map(TeamResponse::from);
    }


    @Override
    @Transactional
    public TeamResponse getById(UUID id) {
        return this.teamRepository.findById(id)
                .map(TeamResponse::from)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }


    @Transactional
    @Override
    public TeamResponse create(String ownerId, CreateTeamRequest request) {

        if (this.teamRepository.existsByOwnerIdAndName(ownerId, request.name())) {
            throw new TeamWithDuplicateNameException(ownerId, request.name());
        }

        Team team = Team.builder()
                .ownerId(ownerId)
                .name(request.name())
                .build();

        //owner team member
        TeamMember teamMember = TeamMember.builder()
                .userId(ownerId)
                .team(team)
                .build();
        team.addTeamMember(teamMember);
        team = this.teamRepository.save(team);
        log.debug("Created new team: name={}, userId={}", request.name(), ownerId);
        return TeamResponse.from(team);
    }

    @Transactional
    @Override
    public void update(UUID id, UpdateTeamRequest request) {
        Team team = this.teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
        if (request.name() != null ) {
            if(this.teamRepository.existsByOwnerIdAndName(team.getOwnerId(), request.name())) {
                throw new TeamWithDuplicateNameException(team.getOwnerId(), request.name());
            }
            team.setName(request.name());
        }
        this.teamRepository.save(team);
        log.debug("Updated team: id={}", id);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        this.teamRepository.deleteById(id);
        log.debug("Deleted team: id={}", id);
    }


}
