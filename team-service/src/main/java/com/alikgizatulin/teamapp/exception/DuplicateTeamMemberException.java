package com.alikgizatulin.teamapp.exception;

import com.alikgizatulin.commonlibrary.exception.DuplicateException;

import java.util.UUID;

import static com.alikgizatulin.teamapp.constant.ErrorCodes.DUPLICATE_TEAM_MEMBER;

public class DuplicateTeamMemberException extends DuplicateException {
    public DuplicateTeamMemberException(UUID teamId, String userId) {
        super(DUPLICATE_TEAM_MEMBER, new Object[]{teamId,userId});
    }
}
