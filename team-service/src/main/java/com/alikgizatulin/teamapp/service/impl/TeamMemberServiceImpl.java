package com.alikgizatulin.teamapp.service.impl;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamMember;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;
import com.alikgizatulin.teamapp.exception.DuplicateTeamMemberException;
import com.alikgizatulin.teamapp.exception.TeamMemberNotFoundException;
import com.alikgizatulin.teamapp.exception.TeamNotFoundException;
import com.alikgizatulin.teamapp.repository.TeamMemberRepository;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import com.alikgizatulin.teamapp.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public TeamMember getById(UUID id) {
        return this.teamMemberRepository.findById(id)
                .orElseThrow(() -> new TeamMemberNotFoundException(id));
    }

    @Transactional
    @Override
    public TeamMember create(String userId, UUID teamId, TeamMemberStatus status) {

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
        teamMember = this.teamMemberRepository.save(teamMember);
        log.debug("Created new team member: id={}, teamId={}",teamMember.getId(),teamId);
        return teamMember;
    }

}
