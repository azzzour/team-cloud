package com.alikgizatulin.teamapp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TeamMemberSummaryResponse(UUID id, String username) {

    public static TeamMemberSummaryResponse fromTeamMemberResponse(TeamMemberResponse member) {
        return new TeamMemberSummaryResponse(
                member.id(),
                member.username()
        );
    }
}
