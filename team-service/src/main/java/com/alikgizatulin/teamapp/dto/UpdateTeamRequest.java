package com.alikgizatulin.teamapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateTeamRequest(
        @Length(min = 5, max = 20, message = "{update.team.request.name.length.constraint}")
        String name
){

}
