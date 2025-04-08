package com.alikgizatulin.commonlibrary.exception;

import java.util.UUID;

import static com.alikgizatulin.commonlibrary.exception.MessageCode.TEAM_NOT_FOUND;


public class TeamNotFoundException extends NotFoundException {

    public TeamNotFoundException(String message, String messageKey) {
        super(message, messageKey);
    }


    public TeamNotFoundException(UUID id) {
        super(TEAM_NOT_FOUND, new Object[]{id});
    }
}
