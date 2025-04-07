package com.alikgizatulin.teamapp.dto;

import com.alikgizatulin.teamapp.entity.TeamMember;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TeamMemberResponse(UUID id,
                                 String userId,
                                 UUID teamId,
                                 TeamMemberStatus status,
                                 Instant joinedAt,
                                 Instant updatedAt) {

    public static TeamMemberResponse from(TeamMember member) {
        return new TeamMemberResponse(
                member.getId(),
                member.getUserId(),
                member.getTeam().getId(),
                member.getStatus(),
                member.getJoinedAt(),
                member.getUpdatedAt()
        );
    }
}
