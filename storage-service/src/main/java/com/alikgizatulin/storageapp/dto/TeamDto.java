package com.alikgizatulin.storageapp.dto;

import java.util.UUID;

public record TeamDto(
        UUID id,
        String ownerId,
        String name
) {
}
