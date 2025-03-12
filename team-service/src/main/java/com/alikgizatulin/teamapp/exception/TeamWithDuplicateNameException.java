package com.alikgizatulin.teamapp.exception;

import com.alikgizatulin.commonlibrary.exception.HttpRequestException;
import org.springframework.http.HttpStatus;

import static com.alikgizatulin.teamapp.constant.ErrorCodes.TEAM_WITH_DUPLICATE_NAME;

public class TeamWithDuplicateNameException extends HttpRequestException {
    public TeamWithDuplicateNameException(String userId, String teamName) {
        super(HttpStatus.BAD_REQUEST, TEAM_WITH_DUPLICATE_NAME, new Object[]{userId,teamName});
    }
}
