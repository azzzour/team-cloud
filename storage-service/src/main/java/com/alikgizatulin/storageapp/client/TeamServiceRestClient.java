package com.alikgizatulin.storageapp.client;

import com.alikgizatulin.storageapp.dto.TeamDto;
import com.alikgizatulin.storageapp.dto.TeamMemberDto;

import java.util.UUID;

public interface TeamServiceRestClient {
    TeamMemberDto getTeamMemberByUserIdAndTeamId(String userId,UUID teamId);
    TeamMemberDto getTeamMemberById(UUID id);
    TeamDto getTeamById(UUID id);
}
