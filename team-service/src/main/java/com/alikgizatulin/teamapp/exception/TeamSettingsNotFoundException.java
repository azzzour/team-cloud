package com.alikgizatulin.teamapp.exception;

import com.alikgizatulin.commonlibrary.exception.NotFoundException;
import static com.alikgizatulin.teamapp.constant.ErrorCodes.TEAM_SETTINGS_NOT_FOUND;

import java.util.UUID;

public class TeamSettingsNotFoundException extends NotFoundException {
    public TeamSettingsNotFoundException(String message, String messageKey) {
        super(message, messageKey);
    }

    public TeamSettingsNotFoundException(UUID id) {
        super(TEAM_SETTINGS_NOT_FOUND, new Object[]{id});
    }
}
