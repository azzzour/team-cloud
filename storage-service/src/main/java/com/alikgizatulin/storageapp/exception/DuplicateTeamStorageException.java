package com.alikgizatulin.storageapp.exception;

import com.alikgizatulin.commonlibrary.exception.DuplicateException;

import java.util.UUID;

import static com.alikgizatulin.storageapp.constant.ErrorCodes.DUPLICATE_TEAM_STORAGE;

public class DuplicateTeamStorageException extends DuplicateException {
    public DuplicateTeamStorageException(String message, String messageKey) {
        super(message, messageKey);
    }

    public DuplicateTeamStorageException(UUID id) {
        super(DUPLICATE_TEAM_STORAGE,new Object[]{id});
    }
}
