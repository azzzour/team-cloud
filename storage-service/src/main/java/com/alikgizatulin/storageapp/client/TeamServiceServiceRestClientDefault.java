package com.alikgizatulin.storageapp.client;

import com.alikgizatulin.commonlibrary.exception.TeamMemberNotFoundException;
import com.alikgizatulin.commonlibrary.exception.TeamNotFoundException;
import com.alikgizatulin.storageapp.dto.TeamDto;
import com.alikgizatulin.storageapp.dto.TeamMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class TeamServiceServiceRestClientDefault implements TeamServiceRestClient {

    private final RestClient restClient;
    @Override
    public TeamMemberDto getTeamMemberByUserIdAndTeamId(String userId,UUID teamId) {
        log.debug("API call. TeamServiceRestClient: getByTeamId");
        try {
            return this.restClient
                    .get()
                    .uri("/api/v1/team-members/by-team/{teamId}/{userId}",teamId,userId)
                    .retrieve()
                    .body(TeamMemberDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new TeamMemberNotFoundException(userId, teamId);
        }
    }

    @Override
    public TeamMemberDto getTeamMemberById(UUID id) {
        log.debug("API call. TeamServiceRestClient: getTeamMemberById");
        try{
            return this.restClient
                    .get()
                    .uri("/api/v1/team-members/{memberId}/details",id)
                    .retrieve()
                    .body(TeamMemberDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new TeamMemberNotFoundException(id);
        }
    }

    @Override
    public TeamDto getTeamById(UUID id) {
        log.debug("API call. TeamServiceRestClient: getTeamById");
        try {
            return this.restClient
                    .get()
                    .uri("/api/v1/teams/{teamId}/details",id)
                    .retrieve()
                    .body(TeamDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new TeamNotFoundException(id);
        }
    }
}
