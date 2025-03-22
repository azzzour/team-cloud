package com.alikgizatulin.teamapp.service.impl;

import com.alikgizatulin.teamapp.dto.CreateTeamRequest;
import com.alikgizatulin.teamapp.dto.UpdateTeamRequest;
import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamMember;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;
import com.alikgizatulin.teamapp.exception.DuplicateTeamMemberException;
import com.alikgizatulin.teamapp.exception.TeamMemberNotFoundException;
import com.alikgizatulin.teamapp.exception.TeamNotFoundException;
import com.alikgizatulin.teamapp.exception.TeamWithDuplicateNameException;
import com.alikgizatulin.teamapp.repository.TeamMemberRepository;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import com.alikgizatulin.teamapp.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    @Override
    public Page<Team> getAll(String userId, String name, Pageable pageable) {
        return this.teamRepository.findAllUserTeams(userId, name, pageable);
    }


    @Override
    @Transactional
    public Team getById(UUID id) {
        return this.teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }


    @Transactional
    @Override
    public Team create(String ownerId, CreateTeamRequest request) {

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
        return team;
    }

    @Transactional
    @Override
    public void addMember(UUID teamId, String userId, TeamMemberStatus status) {
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        if(this.teamMemberRepository.existsByUserIdAndTeamId(userId,teamId)) {
            throw new DuplicateTeamMemberException(teamId,userId);
        }
        TeamMember teamMember = TeamMember.builder()
                .userId(userId)
                .team(team)
                .status(status)
                .build();

        team.addTeamMember(teamMember);
        team.setMemberCount(team.getMemberCount() + 1);
        this.teamRepository.save(team);
        log.debug("Added new team member: userId={}, teamId={}",userId,teamId);
    }


    @Transactional
    @Override
    public void hardDeleteMember(UUID teamId, UUID teamMemberId) {
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        TeamMember teamMember = this.teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new TeamMemberNotFoundException(teamMemberId));
        team.removeTeamMember(teamMember);
        team.setMemberCount(Math.max(team.getMemberCount() - 1, 0));
        this.teamRepository.save(team);
        log.debug("Hard deleted team member: id={}",teamMemberId);
    }

    @Transactional
    @Override
    public void softDeleteMember(UUID teamId, UUID teamMemberId) {
        if(!this.teamRepository.existsById(teamId)) {
            throw new TeamNotFoundException(teamId);
        }
        TeamMember teamMember = this.teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new TeamMemberNotFoundException(teamMemberId));
        if(Objects.equals(teamMember.getStatus(),TeamMemberStatus.REMOVED)) {
            log.debug("Team member already soft deleted: id={}",teamMemberId);
            return;
        }
        teamMember.setStatus(TeamMemberStatus.REMOVED);
        this.teamMemberRepository.save(teamMember);
        log.debug("Soft deleted team member: id={}",teamMemberId);
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
