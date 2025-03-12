package com.alikgizatulin.teamapp.mapper;


import com.alikgizatulin.teamapp.dto.CreateTeamWithSettingsRequest;
import com.alikgizatulin.teamapp.entity.Team;

public class CreateTeamRequestToTeamMapper implements MapperToEntity<CreateTeamWithSettingsRequest, Team>{
    @Override
    public Team toEntity(CreateTeamWithSettingsRequest dto) {
        return Team.builder()
                .name(dto.name())
                .build();
    }
}
