package com.alikgizatulin.teamapp.client;

import com.alikgizatulin.commonlibrary.dto.UserSummaryDto;

import java.util.List;

public interface UserRestClient {
    List<UserSummaryDto> getUsersByIds(List<String> ids);

    UserSummaryDto getById(String id);
}
