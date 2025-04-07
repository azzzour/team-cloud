package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.dto.TeamMemberResponse;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TeamMemberService {

    TeamMemberResponse getById(UUID id);
    Page<TeamMemberResponse> getTeamMembers(UUID teamId, Pageable pageable);

    TeamMemberResponse getByUserIdAndTeamId(String userId, UUID teamId);

    void create(String userId, UUID teamId, TeamMemberStatus status);

    void softDeleteById(UUID id);
    void hardDeleteById(UUID id);

}
