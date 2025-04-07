package com.alikgizatulin.teamapp.controller;

import com.alikgizatulin.commonlibrary.dto.UserSummaryDto;
import com.alikgizatulin.teamapp.client.UserRestClient;
import com.alikgizatulin.teamapp.dto.TeamMemberResponse;
import com.alikgizatulin.teamapp.dto.TeamMemberWithUserResponse;
import com.alikgizatulin.teamapp.dto.TeamMemberWithUserSimpleResponse;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;
import com.alikgizatulin.teamapp.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team-members")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;
    private final UserRestClient userRestClient;


    @PreAuthorize("@teamSecurity.isMember(#teamId,authentication.name)")
    @GetMapping("/by-team/{teamId}")
    public ResponseEntity<PagedModel<TeamMemberWithUserSimpleResponse>> getMembers(@PathVariable("teamId") UUID teamId,
                                                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                                                   @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<TeamMemberResponse> members = this.teamMemberService.getTeamMembers(teamId, pageRequest);

        List<String> userIds = members.stream()
                .map(TeamMemberResponse::userId)
                .toList();

        List<UserSummaryDto> users = this.userRestClient.getUsersByIds(userIds);
        Map<String, UserSummaryDto> userMap = users.stream()
                .collect(Collectors.toMap(UserSummaryDto::getId, Function.identity()));

        List<TeamMemberWithUserSimpleResponse> result = members.stream()
                .map(member -> {
                    UserSummaryDto user = userMap.get(member.userId());
                    if (user == null) {
                        throw new IllegalStateException("User not found for userId: " + member.userId());
                    }
                    return TeamMemberWithUserSimpleResponse.from(member, user);
                })
                .toList();

        var bodyResponse = new PagedModel<>
                (new PageImpl<>(result, pageRequest, members.getTotalElements()));
        return ResponseEntity.ok(bodyResponse);
    }

    //info about me in team
    //@PreAuthorize("@teamSecurity.isMember(#teamId,authentication.name)")
    @GetMapping("/by-team/{teamId}/me")
    public ResponseEntity<TeamMemberWithUserResponse> getMyInfoInTeam(@PathVariable("teamId") UUID teamId,
                                                                      Authentication authentication) {
        String userId = authentication.getName();
        TeamMemberResponse member = this.teamMemberService
                .getByUserIdAndTeamId(userId, teamId);
        UserSummaryDto user = this.userRestClient.getById(userId);

        return ResponseEntity.ok(TeamMemberWithUserResponse.from(member, user));
    }

    @PreAuthorize("@teamSecurity.isSameTeamMember(#memberId,authentication.name)")
    @GetMapping("/{memberId}")
    public ResponseEntity<TeamMemberWithUserSimpleResponse> getMember(@PathVariable("memberId") UUID memberId) {
        TeamMemberResponse member = this.teamMemberService.getById(memberId);
        UserSummaryDto user = this.userRestClient.getById(member.userId());
        return ResponseEntity.ok(TeamMemberWithUserSimpleResponse.from(member, user));
    }


    @PreAuthorize("@teamSecurity.isOwnerOfMemberTeam(#memberId, authentication.name)")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable("memberId") UUID memberId,
                                             @RequestParam(value = "isHard") boolean isHard) {
        if (isHard) {
            this.teamMemberService.hardDeleteById(memberId);
        } else {
            this.teamMemberService.softDeleteById(memberId);
        }
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@teamSecurity.isOwner(#teamId, authentication.name)")
    @PostMapping("/by-team/{teamId}")
    public ResponseEntity<Void> addMember(@PathVariable("teamId") UUID teamId,
                                          @RequestParam("userId") String userId) {
        this.teamMemberService.create(userId, teamId, TeamMemberStatus.NO_STORAGE);
        return ResponseEntity.noContent().build();
    }
}
