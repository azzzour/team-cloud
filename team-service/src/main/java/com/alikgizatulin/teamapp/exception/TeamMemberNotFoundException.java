package com.alikgizatulin.teamapp.exception;

import com.alikgizatulin.commonlibrary.exception.NotFoundException;

import java.util.UUID;

import static com.alikgizatulin.teamapp.constant.ErrorCodes.*;

public class TeamMemberNotFoundException extends NotFoundException {
    public TeamMemberNotFoundException(String message, String messageKey) {
        super(message, messageKey);
    }

    public TeamMemberNotFoundException(UUID id) {
        super(TEAM_MEMBER_NOT_FOUND, new Object[]{id});
    }

    public TeamMemberNotFoundException(String userId, UUID teamId) {
        super(USER_NOT_FOUND_IN_TEAM, new Object[]{userId, teamId});
    }

}
