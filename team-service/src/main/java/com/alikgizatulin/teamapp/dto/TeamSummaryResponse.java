package com.alikgizatulin.teamapp.dto;

import com.alikgizatulin.teamapp.entity.Team;

import java.util.UUID;

public record TeamSummaryResponse (UUID id, String name) {

    public static TeamSummaryResponse fromTeam(Team team) {
        return new TeamSummaryResponse(
                team.getId(),
                team.getName()
        );
    }
}
