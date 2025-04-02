package com.alikgizatulin.storageapp.exception;

import com.alikgizatulin.commonlibrary.exception.DuplicateException;

import java.util.UUID;

import static com.alikgizatulin.storageapp.constant.ErrorCodes.DUPLICATE_FILE;

public class DuplicateFileException extends DuplicateException {
    public DuplicateFileException(String message, String messageKey) {
        super(message, messageKey);
    }

    public DuplicateFileException(UUID memberStorageId, String name, UUID parentFolderId) {
        super(DUPLICATE_FILE, new Object[]{memberStorageId, name, parentFolderId});
    }
    public DuplicateFileException(UUID memberStorageId, String name) {
        super(DUPLICATE_FILE, new Object[]{memberStorageId, name});
    }
}
