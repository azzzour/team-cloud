package com.alikgizatulin.teamapp.exception;

import com.alikgizatulin.commonlibrary.exception.NotFoundException;

import java.util.UUID;

import static com.alikgizatulin.teamapp.constant.ErrorCodes.USER_NOT_FOUND_IN_TEAM;

public class UserNotFoundInTeam extends NotFoundException {
    public UserNotFoundInTeam(String message, String messageKey) {
        super(message, messageKey);
    }

    public UserNotFoundInTeam(String userId, UUID teamId) {
        super(USER_NOT_FOUND_IN_TEAM, new Object[]{userId, teamId});
    }
}
