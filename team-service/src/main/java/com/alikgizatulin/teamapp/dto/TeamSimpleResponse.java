package com.alikgizatulin.teamapp.dto;

import java.util.UUID;

public record TeamSimpleResponse(UUID id, String name, int memberCount) {

    public static TeamSimpleResponse from(TeamResponse team) {
        return new TeamSimpleResponse(
                team.id(),
                team.name(),
                team.memberCount()
        );
    }
}
