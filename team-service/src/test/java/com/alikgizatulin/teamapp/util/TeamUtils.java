package com.alikgizatulin.teamapp.util;

import com.alikgizatulin.teamapp.entity.Team;

import java.util.UUID;

public class TeamUtils {


    public static Team getTestTeamPersisted(UUID teamId, String ownerId) {
        return Team.builder()
                .id(teamId)
                .ownerId(ownerId)
                .name("test_name")
                .build();
    }

    public static Team getTestTeamTransient(String ownerId) {
        return Team.builder()
                .name("test_name")
                .ownerId(ownerId)
                .build();
    }
}
