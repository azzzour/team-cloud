package com.alikgizatulin.storageapp.exception;

import com.alikgizatulin.commonlibrary.exception.NotFoundException;

import java.util.UUID;

import static com.alikgizatulin.storageapp.constant.ErrorCodes.FILE_NOT_FOUND;

public class FileNotFoundException extends NotFoundException {
    public FileNotFoundException(String message, String messageKey) {
        super(message, messageKey);
    }

    public FileNotFoundException(UUID fileId) {
        super(FILE_NOT_FOUND,new Object[]{fileId});
    }
}
