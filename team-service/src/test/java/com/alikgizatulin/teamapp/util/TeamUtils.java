package com.alikgizatulin.teamapp.util;

import com.alikgizatulin.teamapp.entity.Team;

import java.util.UUID;

public class TeamUtils {

    public static Team getTestTeamByTeamIdAndOwnerId(UUID teamId,String ownerId) {
        return Team.builder()
                .id(teamId)
                .name("test_name")
                .ownerId(ownerId)
                .build();
    }

    public static Team getTestTeamByTeamId(UUID teamId) {
        return Team.builder()
                .id(teamId)
                .name("test_name")
                .ownerId("test_ownerId")
                .build();
    }
}
