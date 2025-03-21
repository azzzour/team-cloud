package com.alikgizatulin.teamapp.exception;

import com.alikgizatulin.commonlibrary.exception.DuplicateException;

import static com.alikgizatulin.teamapp.constant.ErrorCodes.TEAM_WITH_DUPLICATE_NAME;

public class TeamWithDuplicateNameException extends DuplicateException {
    public TeamWithDuplicateNameException(String userId, String teamName) {
        super(TEAM_WITH_DUPLICATE_NAME, new Object[]{userId,teamName});
    }
}
