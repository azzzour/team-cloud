package com.alikgizatulin.teamapp.dto;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamStatus;

import java.time.Instant;
import java.util.UUID;

public record DetailedTeamResponse (UUID id, String name, int memberCount, String ownerId,
                                    TeamStatus status, Instant createdAt){
    public static DetailedTeamResponse fromTeam(Team team) {
        return new DetailedTeamResponse(
                team.getId(),
                team.getName(),
                team.getMemberCount(),
                team.getOwnerId(),
                team.getStatus(),
                team.getCreatedAt()
        );
    }
}
