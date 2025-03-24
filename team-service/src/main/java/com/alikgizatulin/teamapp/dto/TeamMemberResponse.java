package com.alikgizatulin.teamapp.dto;

import com.alikgizatulin.teamapp.entity.TeamMember;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TeamMemberResponse(UUID id,
                                 String username,
                                 TeamMemberStatus status,
                                 Instant joinedAt) {

    public static TeamMemberResponse makeTeamMemberResponse(TeamMember member, String username) {
        return new TeamMemberResponse(
                member.getId(),
                username,
                member.getStatus(),
                member.getJoinedAt()
        );
    }
}
