package com.alikgizatulin.teamapp.dto;

import com.alikgizatulin.commonlibrary.dto.UserSummaryDto;
import com.alikgizatulin.teamapp.entity.TeamMemberStatus;

import java.time.Instant;
import java.util.UUID;

public record TeamMemberWithUserResponse(UUID id,
                                         String userId,
                                         String username,
                                         UUID teamId,
                                         TeamMemberStatus status,
                                         Instant joinedAt,
                                         Instant updatedAt) {

    public static TeamMemberWithUserResponse from(TeamMemberResponse member,
                                                  UserSummaryDto user) {
        return new TeamMemberWithUserResponse(
                member.id(),
                member.userId(),
                user.getUsername(),
                member.teamId(),
                member.status(),
                member.joinedAt(),
                member.updatedAt()
        );
    }
}
