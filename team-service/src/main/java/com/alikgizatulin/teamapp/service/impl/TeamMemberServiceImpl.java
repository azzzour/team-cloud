package com.alikgizatulin.teamapp.service.impl;

import com.alikgizatulin.commonlibrary.dto.UserSummaryDto;
import com.alikgizatulin.teamapp.client.UserRestClient;
import com.alikgizatulin.teamapp.dto.TeamMemberResponse;
import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamMember;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;
import com.alikgizatulin.teamapp.exception.DuplicateTeamMemberException;
import com.alikgizatulin.teamapp.exception.TeamMemberNotFoundException;
import com.alikgizatulin.teamapp.exception.TeamNotFoundException;
import com.alikgizatulin.teamapp.exception.UserNotFoundInTeam;
import com.alikgizatulin.teamapp.repository.TeamMemberRepository;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import com.alikgizatulin.teamapp.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

    private final UserRestClient userRestClient;

    private final TeamMemberRepository teamMemberRepository;

    private final TeamRepository teamRepository;


    @Override
    public Page<TeamMemberResponse> getTeamMembers(UUID teamId, Pageable pageable) {
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        Page<TeamMember> members = this.teamMemberRepository
                .findAllByTeam(team, pageable);
        List<String> ids = members.map((member) -> member.getUserId()).toList();
        List<UserSummaryDto> users = this.userRestClient.getUsersByIds(ids);
        List<TeamMemberResponse> result = IntStream.range(0, users.size())
                .mapToObj(i -> TeamMemberResponse.makeTeamMemberResponse(
                        members.getContent().get(i), users.get(i).getUsername()))
                .toList();
        return new PageImpl<>(result,pageable,members.getTotalElements());
    }

    @Override
    public TeamMemberResponse getById(UUID id) {
        TeamMember member = this.teamMemberRepository.findById(id)
                .orElseThrow(() -> new TeamMemberNotFoundException(id));
        var user = this.userRestClient.getById(member.getUserId());
        return TeamMemberResponse.makeTeamMemberResponse(member,user.getUsername());
    }

    @Override
    public TeamMemberResponse getByUserIdAndTeamId(String userId, UUID teamId) {
        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        TeamMember member = this.teamMemberRepository.findByUserIdAndTeam(userId,team)
                .orElseThrow(() -> new UserNotFoundInTeam(userId,teamId));
        var user = this.userRestClient.getById(member.getUserId());
        return TeamMemberResponse.makeTeamMemberResponse(member,user.getUsername());
    }

    @Transactional
    @Override
    public void create(String userId, UUID teamId, TeamMemberStatus status) {

        Team team = this.teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        if(this.teamMemberRepository.existsByUserIdAndTeamId(userId,teamId)) {
            throw new DuplicateTeamMemberException(teamId,userId);
        }

        TeamMember member = TeamMember.builder()
                .userId(userId)
                .team(team)
                .status(status)
                .build();
        this.teamMemberRepository.save(member);
        log.debug("Created new team member: id={}, teamId={}",member.getId(),teamId);
    }


}
