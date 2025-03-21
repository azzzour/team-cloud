package com.alikgizatulin.teamapp.dto;

import com.alikgizatulin.teamapp.entity.TeamMember;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TeamMemberResponse(String userId, UUID teamId, Instant joinedAt) {

    public static TeamMemberResponse fromTeamMember(TeamMember teamMember) {
        return new TeamMemberResponse(
                teamMember.getUserId(),
                teamMember.getTeam().getId(),
                teamMember.getJoinedAt()
        );
    }
}
