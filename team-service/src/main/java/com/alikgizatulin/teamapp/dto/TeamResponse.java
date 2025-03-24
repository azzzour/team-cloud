package com.alikgizatulin.teamapp.dto;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;
import java.util.UUID;



@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TeamResponse(UUID id, String name, int memberCount,
                           String ownerId, TeamStatus status, Instant createdAt) {
    public static TeamResponse fromTeam(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getMemberCount(),
                team.getOwnerId(),
                team.getStatus(),
                team.getCreatedAt()
        );
    }

}
