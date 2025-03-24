package com.alikgizatulin.teamapp.controller;

import com.alikgizatulin.teamapp.dto.TeamMemberResponse;
import com.alikgizatulin.teamapp.dto.TeamMemberSummaryResponse;
import com.alikgizatulin.teamapp.service.TeamMemberService;
import com.alikgizatulin.teamapp.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/teams/{teamId}/members")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    private final TeamService teamService;

    @PreAuthorize("@teamSecurity.isMember(#teamId,authentication.name)")
    @GetMapping()
    public ResponseEntity<PagedModel<TeamMemberSummaryResponse>> getMembers(@PathVariable("teamId") UUID teamId,
                                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                                            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageRequest = PageRequest.of(page,size);
        var members = this.teamMemberService.getTeamMembers(teamId,pageRequest)
                .map(TeamMemberSummaryResponse::fromTeamMemberResponse);
        return ResponseEntity.ok(new PagedModel<>(members));
    }

    //info about me in team
    @PreAuthorize("@teamSecurity.isMember(#teamId,authentication.name)")
    @GetMapping("/me")
    public ResponseEntity<TeamMemberResponse> getMyTeamInfo(@PathVariable("teamId") UUID teamId,
                                                            Authentication authentication) {
        String userId = authentication.getName();
        TeamMemberResponse bodyResponse = this.teamMemberService
                .getByUserIdAndTeamId(userId,teamId);
        return ResponseEntity.ok(bodyResponse);
    }

    @PreAuthorize("@teamSecurity.isOwner(#teamId,authentication.name)")
    @GetMapping("/{memberId}")
    public ResponseEntity<TeamMemberResponse> getMember(@PathVariable("teamId") UUID teamId,
                                                        @PathVariable("memberId") UUID memberId) {
        TeamMemberResponse bodyResponse = this.teamMemberService
                .getById(memberId);
        return ResponseEntity.ok(bodyResponse);
    }



    @PreAuthorize("@teamSecurity.isOwner(#teamId, authentication.name)")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable("teamId") UUID teamId,
                                             @PathVariable("memberId") UUID memberId,
                                             @RequestParam(value = "isHard") boolean isHard) {
        if (isHard) {
            this.teamService.hardDeleteMember(teamId, memberId);
        } else {
            this.teamService.softDeleteMember(teamId, memberId);
        }
        return ResponseEntity.noContent().build();
    }

    /* @PreAuthorize("@teamSecurity.isOwner(#teamId, authentication.name)")
    @PostMapping
    public ResponseEntity<Void> addMember(@PathVariable UUID teamId,@PathVariable("userId") String userId) {
        this.teamService.addMember(teamId,userId, TeamMemberStatus.NO_STORAGE);
        return ResponseEntity.noContent().build();
    }*/
}
