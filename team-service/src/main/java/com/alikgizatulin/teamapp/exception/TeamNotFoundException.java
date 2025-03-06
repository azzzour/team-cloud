package com.alikgizatulin.teamapp.exception;

import com.alikgizatulin.commonlibrary.exception.NotFoundException;

import java.util.UUID;

import static com.alikgizatulin.teamapp.constant.ErrorCodes.TEAM_NOT_FOUND;

public class TeamNotFoundException extends NotFoundException {

    public TeamNotFoundException(String message, String messageKey) {
        super(message, messageKey);
    }

    public TeamNotFoundException(UUID teamId) {
        super(TEAM_NOT_FOUND, new Object[]{teamId});
    }
}
