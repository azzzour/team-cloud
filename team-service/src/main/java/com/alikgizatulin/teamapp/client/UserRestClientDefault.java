package com.alikgizatulin.teamapp.client;

import com.alikgizatulin.commonlibrary.dto.UserSummaryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class UserRestClientDefault implements UserRestClient{
    private final RestClient restClient;
    @Override
    public List<UserSummaryDto> getUsersByIds(List<String> ids) {
        log.debug("API call. UserRestClient: getUsersByIds");
        return this.restClient
                .post()
                .uri("api/v1/internal/users/by-ids")
                .body(ids)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public UserSummaryDto getById(String id) {
        log.debug("API call. UserRestClient: getById");
        return this.restClient
                .get()
                .uri("api/v1/internal/users/{userId}",id)
                .retrieve()
                .body(UserSummaryDto.class);
    }
}
