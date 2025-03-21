package com.alikgizatulin.teamapp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateTeamRequest(
                                @NotNull(message=  "{create.team.request.name.not.null}")
                                @Length(min = 5, max = 20,message = "{create.team.request.name.length.constraint}")
                                String name,
                                @NotNull(message = "{create.team.request.storage.size.not.null.constraint}")
                                @Min(value = 1000000000L,
                                        message = "{create.team.request.storage.size.min.constraint}")
                                @Max(value = 1000000000000L,
                                        message = "{create.team.request.storage.size.max.constraint}")
                                long storageSize,
                                @NotNull(message = "{create.team.request.max.file.size.not.null.constraint}")

                                @Min(value = 10000000L,
                                        message = "{create.team.request.min.max.file.size.constraint}=")
                                @Max(value = 10000000000L,
                                        message = "{create.team.request.max.max.file.size.constraint}")
                                long maxFileSize
                                ) {
}
