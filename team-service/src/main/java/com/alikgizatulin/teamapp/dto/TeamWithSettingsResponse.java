package com.alikgizatulin.teamapp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeamWithSettingsResponse {

    private UUID id;

    private String name;

    private String ownerId;

    private Instant teamCreatedAt;

    private Instant teamUpdatedAt;

    private UUID defaultRoleId;

    private boolean deleteFilesOnExit;

    private long totalStorageLimit;

    private long maxUsers;

    private long maxFileSize;

    private Instant teamSettingsCreatedAt;

    private Instant teamSettingsUpdatedAt;


}
