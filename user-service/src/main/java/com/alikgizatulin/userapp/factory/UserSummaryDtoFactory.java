package com.alikgizatulin.userapp.factory;

import com.alikgizatulin.commonlibrary.dto.UserSummaryDto;
import com.alikgizatulin.userapp.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserSummaryDtoFactory {

    public UserSummaryDto makeUserSummaryDto(UserModel user) {
        return UserSummaryDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
