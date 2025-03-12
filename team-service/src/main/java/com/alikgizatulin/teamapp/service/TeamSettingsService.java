package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.entity.TeamSettings;

import java.util.UUID;

public interface TeamSettingsService {
    TeamSettings getById(UUID id);

    TeamSettings create(TeamSettings teamSettings);
}
