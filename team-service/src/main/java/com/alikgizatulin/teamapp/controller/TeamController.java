package com.alikgizatulin.teamapp.controller;

import com.alikgizatulin.teamapp.dto.CreateTeamRequest;
import com.alikgizatulin.teamapp.dto.TeamResponse;
import com.alikgizatulin.teamapp.dto.UpdateTeamRequest;
import com.alikgizatulin.teamapp.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<PagedModel<TeamResponse>> getTeams(
            @RequestParam(required = false,defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"name"));
        var teams = this.teamService.getAll(userId,name,pageable).map(TeamResponse::fromTeam);
        return ResponseEntity.ok(new PagedModel<>(teams));
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable("teamId") UUID teamId) {
        return ResponseEntity.ok(TeamResponse.fromTeam(this.teamService.getById(teamId)));
    }

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@RequestBody @Valid CreateTeamRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder,
                                                   Authentication authentication) {
        String ownerId = authentication.getName();
        var bodyResponse = TeamResponse.fromTeam(this.teamService.create(ownerId, request));
        return ResponseEntity.created(uriComponentsBuilder
                .path("api/v1/teams/{teamId}")
                .buildAndExpand(bodyResponse.id())
                .toUri()
        ).body(bodyResponse);
    }

   /* @GetMapping("/{teamId}/details")
    public ResponseEntity<DetailedTeamResponse> getDetailedTeam(@PathVariable("teamId") UUID teamId) {
        return ResponseEntity.ok(DetailedTeamResponse.fromTeam(this.teamService.getById(teamId)));
    }*/

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("teamId") UUID teamId) {
        this.teamService.deleteById(teamId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{teamId}")
    public ResponseEntity<Void> updateTeam(@PathVariable("teamId") UUID teamId,
                                                           @RequestBody @Valid UpdateTeamRequest request) {
        this.teamService.update(teamId,request);
        return ResponseEntity.noContent().build();
    }

    /*@PostMapping("/{teamId}/members")
    public ResponseEntity<Void> addMember(@PathVariable UUID teamId,@PathVariable("userId") String userId) {
        this.teamService.addMember(teamId,userId, TeamMemberStatus.SETTING_UP);
        return ResponseEntity.noContent().build();
    }*/

    @DeleteMapping("/{teamId}/members/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable("teamId") UUID teamId,@PathVariable("memberId") UUID memberId,
                                             @RequestParam(value = "isHard") boolean isHard) {
        if(isHard) {
            this.teamService.hardDeleteMember(teamId,memberId);
        } else {
            this.teamService.softDeleteMember(teamId, memberId);
        }
        return ResponseEntity.noContent().build();
    }


}
