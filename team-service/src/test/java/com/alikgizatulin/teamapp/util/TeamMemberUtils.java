package com.alikgizatulin.teamapp.util;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamMember;

import java.util.UUID;

public class TeamMemberUtils {

    public static TeamMember getTestTeamMemberPersisted(String userId, Team team)  {
        return TeamMember.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .team(team)
                .build();
    }

    public static TeamMember getTestTeamMemberTransient(String userId, Team team)  {
        return TeamMember.builder()
                .userId(userId)
                .team(team)
                .build();
    }


}
