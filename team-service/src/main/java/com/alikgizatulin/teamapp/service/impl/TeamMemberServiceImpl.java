package com.alikgizatulin.teamapp.service.impl;

import com.alikgizatulin.commonlibrary.exception.TeamMemberNotFoundException;
import com.alikgizatulin.teamapp.dto.TeamMemberResponse;
import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamMember;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;
import com.alikgizatulin.teamapp.exception.DuplicateTeamMemberException;
import com.alikgizatulin.commonlibrary.exception.TeamNotFoundException;
import com.alikgizatulin.teamapp.repository.TeamMemberRepository;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import com.alikgizatulin.teamapp.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    private final TeamRepository teamRepository;

    @Override
    public TeamMemberResponse getById(UUID id) {
        return this.teamMemberRepository.findById(id)
                .map(TeamMemberResponse::from)
                .orElseThrow(() -> new TeamMemberNotFoundException(id));
    }

    @Override
    public Page<TeamMemberResponse> getTeamMembers(UUID teamId, Pageable pageable) {
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        return this.teamMemberRepository
                .findAllByTeam(team, pageable).map(TeamMemberResponse::from);
    }

    @Override
    public TeamMemberResponse getByUserIdAndTeamId(String userId, UUID teamId) {
        Team team = this.teamRepository.getReferenceById(teamId);
        return this.teamMemberRepository.findByUserIdAndTeam(userId,team)
                .map(TeamMemberResponse::from)
                .orElseThrow(() -> new TeamMemberNotFoundException(userId,teamId));
    }

    @Transactional
    @Override
    public void create(String userId, UUID teamId, TeamMemberStatus status) {
        if(this.teamMemberRepository.existsByUserIdAndTeamId(userId,teamId)) {
            throw new DuplicateTeamMemberException(teamId,userId);
        }
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        TeamMember member = TeamMember.builder()
                .userId(userId)
                .team(team)
                .status(status)
                .build();
        team.setMemberCount(team.getMemberCount() + 1);
        this.teamMemberRepository.save(member);
        this.teamRepository.save(team);
        log.debug("Created new team member: id={}, teamId={}",member.getId(),teamId);
    }

    @Transactional
    @Override
    public void softDeleteById(UUID id) {
        TeamMember member = this.teamMemberRepository.findById(id)
                .orElseThrow(() -> new TeamMemberNotFoundException(id));
        member.setStatus(TeamMemberStatus.REMOVED);
        this.teamMemberRepository.save(member);
        log.debug("Soft deleted team member: id={}",id);
    }

    @Transactional
    @Override
    public void hardDeleteById(UUID id) {
        TeamMember member = this.teamMemberRepository.findByIdWithTeam(id)
                .orElseThrow(() -> new TeamMemberNotFoundException(id));
        Team team = member.getTeam();
        team.setMemberCount(team.getMemberCount() - 1);
        this.teamMemberRepository.deleteById(id);
        this.teamRepository.save(team);
        log.debug("Hard deleted team member: id={}",id);
    }


}
