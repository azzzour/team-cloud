package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.dto.CreateTeamRequest;
import com.alikgizatulin.teamapp.dto.TeamResponse;
import com.alikgizatulin.teamapp.dto.UpdateTeamRequest;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TeamService {
    Page<TeamResponse> getUserTeams(String userId, String name, Pageable pageable);

    TeamResponse getById(UUID id);

    TeamResponse create(String ownerId, CreateTeamRequest request);

    void addMember(UUID teamId, String userId, TeamMemberStatus status);

    void hardDeleteMember(UUID teamId, UUID teamMemberId);

    void softDeleteMember(UUID teamId, UUID teamMemberId);

    void update(UUID id, UpdateTeamRequest request);

    void deleteById(UUID id);
}
