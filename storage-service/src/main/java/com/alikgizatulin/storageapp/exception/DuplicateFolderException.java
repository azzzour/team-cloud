package com.alikgizatulin.storageapp.exception;

import com.alikgizatulin.commonlibrary.exception.DuplicateException;

import java.util.UUID;

import static com.alikgizatulin.storageapp.constant.ErrorCodes.DUPLICATE_FOLDER;

public class DuplicateFolderException extends DuplicateException {
    public DuplicateFolderException(String message, String messageKey) {
        super(message, messageKey);
    }

    public DuplicateFolderException(UUID memberStorageId, String name, UUID parentFolderId) {
        super(DUPLICATE_FOLDER, new Object[]{memberStorageId, name, parentFolderId});
    }
}
