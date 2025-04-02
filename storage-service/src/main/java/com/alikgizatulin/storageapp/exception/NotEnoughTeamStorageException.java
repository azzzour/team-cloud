package com.alikgizatulin.storageapp.exception;

import com.alikgizatulin.commonlibrary.exception.HttpRequestException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.alikgizatulin.storageapp.constant.ErrorCodes.NOT_ENOUGH_TEAM_STORAGE;

public class NotEnoughTeamStorageException extends HttpRequestException {
    public NotEnoughTeamStorageException(UUID teamStorageId, UUID memberId) {
        super(HttpStatus.PAYLOAD_TOO_LARGE, NOT_ENOUGH_TEAM_STORAGE,new Object[]{teamStorageId,memberId});
    }
}
