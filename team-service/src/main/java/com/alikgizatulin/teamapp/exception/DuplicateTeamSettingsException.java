package com.alikgizatulin.teamapp.exception;

import com.alikgizatulin.commonlibrary.exception.HttpRequestException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.alikgizatulin.teamapp.constant.ErrorCodes.DUPLICATE_TEAM_SETTINGS;

public class DuplicateTeamSettingsException extends HttpRequestException {
    public DuplicateTeamSettingsException(UUID teamId) {
        super(HttpStatus.CONFLICT, DUPLICATE_TEAM_SETTINGS, new Object[]{teamId});
    }
}
