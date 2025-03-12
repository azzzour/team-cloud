package com.alikgizatulin.teamapp.factory;

import com.alikgizatulin.teamapp.dto.TeamWithSettingsResponse;
import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamSettings;
import org.springframework.stereotype.Component;

@Component
public class TeamWithSettingsResponseFactory {

    public TeamWithSettingsResponse makeTeamWithSettingsResponse(Team team, TeamSettings settings) {
        return TeamWithSettingsResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .ownerId(team.getOwnerId())
                .teamCreatedAt(team.getCreatedAt())
                .teamUpdatedAt(team.getUpdatedAt())
                .defaultRoleId(settings.getDefaultRoleId())
                .deleteFilesOnExit(settings.getDeleteFilesOnExit())
                .totalStorageLimit(settings.getTotalStorageLimit())
                .maxUsers(settings.getMaxUsers())
                .maxFileSize(settings.getMaxFileSize())
                .teamSettingsCreatedAt(settings.getCreatedAt())
                .teamSettingsUpdatedAt(settings.getUpdatedAt())
                .build();
    }
}
