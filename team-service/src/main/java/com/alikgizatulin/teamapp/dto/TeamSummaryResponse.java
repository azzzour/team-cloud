package com.alikgizatulin.teamapp.dto;

import java.util.UUID;

public record TeamSummaryResponse (UUID id, String name, int memberCount) {

    public static TeamSummaryResponse fromTeamResponse(TeamResponse team) {
        return new TeamSummaryResponse(
                team.id(),
                team.name(),
                team.memberCount()
        );
    }
}
