package com.alikgizatulin.teamapp.util;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamSettings;

public class TeamSettingsUtils {

    public static TeamSettings getTeamSettingsPersisted(Team team) {
        return TeamSettings.builder()
                .id(team.getId())
                .team(team)
                .build();
    }

    public static TeamSettings getTeamSettingsTransient(Team team) {
        return TeamSettings.builder()
                .team(team)
                .build();
    }
}
