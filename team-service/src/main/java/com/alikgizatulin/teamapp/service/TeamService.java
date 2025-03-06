package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.entity.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    Team getTeamById(UUID id);

    List<Team> getTeamsByUsersId(String userId);
}
