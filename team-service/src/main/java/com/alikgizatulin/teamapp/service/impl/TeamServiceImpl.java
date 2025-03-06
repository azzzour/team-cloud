package com.alikgizatulin.teamapp.service.impl;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.exception.TeamNotFoundException;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import com.alikgizatulin.teamapp.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public Team getTeamById(UUID id) {
        return this.teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    @Override
    public List<Team> getTeamsByUsersId(String userId) {
        return this.teamRepository.findAllByOwnerId(userId);
    }
}
