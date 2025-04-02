package com.alikgizatulin.storageapp.exception;


import com.alikgizatulin.commonlibrary.exception.NotFoundException;

import java.util.UUID;

import static com.alikgizatulin.storageapp.constant.ErrorCodes.TEAM_STORAGE_NOT_FOUND;

public class TeamStorageNotFoundException extends NotFoundException {

    public TeamStorageNotFoundException(String message, String messageKey) {
        super(message, messageKey);
    }

    public TeamStorageNotFoundException(UUID id) {
        super(TEAM_STORAGE_NOT_FOUND,new Object[]{id});
    }
}
