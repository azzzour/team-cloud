package com.alikgizatulin.teamapp.controller;

import com.alikgizatulin.teamapp.dto.CreateTeamWithSettingsRequest;
import com.alikgizatulin.teamapp.dto.TeamResponse;
import com.alikgizatulin.teamapp.dto.TeamWithSettingsResponse;
import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.entity.TeamSettings;
import com.alikgizatulin.teamapp.factory.TeamResponseFactory;
import com.alikgizatulin.teamapp.factory.TeamWithSettingsResponseFactory;
import com.alikgizatulin.teamapp.service.TeamService;
import com.alikgizatulin.teamapp.service.TeamSettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamSettingsService teamSettingsService;
    private final TeamWithSettingsResponseFactory teamWithSettingsResponseFactory;
    private final TeamResponseFactory teamResponseFactory;

    private static final String GET_TEAM_WITH_SETTINGS = "/{teamId}";
    private static final String GET_TEAM = "/{teamId}/summary";


    @GetMapping(GET_TEAM)
    public ResponseEntity<TeamResponse> getTeam(@PathVariable("teamId") UUID teamId) {
        Team team = this.teamService.getTeamById(teamId);
        return ResponseEntity.ok(this.teamResponseFactory.makeTeamResponse(team));
    }

    @GetMapping(GET_TEAM_WITH_SETTINGS)
    public ResponseEntity<TeamWithSettingsResponse> getTeamWithSettings(@PathVariable("teamId") UUID teamId) {
        Team team = this.teamService.getTeamById(teamId);
        TeamSettings settings = this.teamSettingsService.getById(teamId);
        return ResponseEntity
                .ok(this.teamWithSettingsResponseFactory.makeTeamWithSettingsResponse(team,settings));
    }

    @PostMapping()
    public ResponseEntity<?> createTeam(@RequestBody @Valid CreateTeamWithSettingsRequest request,
                                        UriComponentsBuilder uriComponentsBuilder,
                                        Authentication authentication) {
        Team team = Team.builder()
                .name(request.name())
                .ownerId(authentication.getName())
                .build();
        TeamSettings teamSettings = TeamSettings.builder()
                .totalStorageLimit(request.totalStorageLimit())
                .maxUsers(request.maxUsers())
                .maxFileSize(request.maxFileSize())
                .deleteFilesOnExit(request.deleteFilesOnExit())
                .defaultRoleId(null)
                .team(team)
                .build();
        Team createdTeam = this.teamService.createWithSettings(team,teamSettings);
        var bodyResponse = this.teamWithSettingsResponseFactory.makeTeamWithSettingsResponse(team,teamSettings);
        return ResponseEntity.created(uriComponentsBuilder
                        .replacePath("api/v1/teams/{teamId}")
                        .build(Map.of("teamId",createdTeam.getId()))
                ).body(bodyResponse);
    }


}
