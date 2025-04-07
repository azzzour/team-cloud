package com.alikgizatulin.teamapp.dto;

import com.alikgizatulin.commonlibrary.dto.UserSummaryDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TeamMemberWithUserSimpleResponse(UUID id,
                                               String userId,
                                               String username) {

    public static TeamMemberWithUserSimpleResponse from(TeamMemberResponse member, UserSummaryDto user) {
        return new TeamMemberWithUserSimpleResponse(
                member.id(),
                member.userId(),
                user.getUsername()
        );
    }
}
