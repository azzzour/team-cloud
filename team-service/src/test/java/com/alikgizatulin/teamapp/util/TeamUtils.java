package com.alikgizatulin.teamapp.util;

import com.alikgizatulin.teamapp.entity.Team;

import java.util.UUID;

public class TeamUtils {


    public static Team getTestTeamPersisted(UUID teamId) {
        return Team.builder()
                .id(teamId)
                .name("test_name")
                .ownerId("test_ownerId")
                .build();
    }

    public static Team getTestTeamTransient() {
        return Team.builder()
                .name("test_name")
                .ownerId("test_ownerId")
                .build();
    }
}
