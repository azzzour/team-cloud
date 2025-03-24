package com.alikgizatulin.teamapp.dto;

import com.alikgizatulin.teamapp.entity.Team;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.UUID;



@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TeamResponse(UUID id, String name, long memberCount) {
    public static TeamResponse fromTeam(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getMemberCount()
        );
    }

}
