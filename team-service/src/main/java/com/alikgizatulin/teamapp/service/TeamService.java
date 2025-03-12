package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamSettings;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    Team getTeamById(UUID id);
    List<Team> getTeamsByUsersId(String userId);

    Team create(Team team);
    Team createWithSettings(Team team, TeamSettings teamSettings);
}
