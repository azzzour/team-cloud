package com.alikgizatulin.teamapp.factory;

import com.alikgizatulin.teamapp.dto.TeamResponse;
import com.alikgizatulin.teamapp.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamResponseFactory {

    public TeamResponse makeTeamResponse(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .ownerId(team.getOwnerId())
                .createdAt(team.getCreatedAt())
                .updatedAt(team.getUpdatedAt())
                .build();
    }
}
