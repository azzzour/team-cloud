package com.alikgizatulin.storageapp.dto;

import java.util.UUID;

public record TeamMemberDto (
        UUID id,
        String userId,
        UUID teamId
) {
}
