package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.entity.TeamMember;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;

import java.util.UUID;

public interface TeamMemberService {

    TeamMember getById(UUID id);

    TeamMember create(String userId, UUID teamId, TeamMemberStatus status);


}
