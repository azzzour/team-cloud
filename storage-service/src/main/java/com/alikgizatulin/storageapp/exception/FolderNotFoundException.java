package com.alikgizatulin.storageapp.exception;

import com.alikgizatulin.commonlibrary.exception.NotFoundException;

import java.util.UUID;

import static com.alikgizatulin.storageapp.constant.ErrorCodes.FOLDER_NOT_FOUND;

public class FolderNotFoundException extends NotFoundException {
    public FolderNotFoundException(String message, String messageKey) {
        super(message, messageKey);
    }

    public FolderNotFoundException(UUID id) {
        super(FOLDER_NOT_FOUND,new Object[]{id});
    }
}
