package com.alikgizatulin.teamapp.controller;

import com.alikgizatulin.teamapp.dto.CreateTeamRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/team")
@RequiredArgsConstructor
public class TeamController {

    private static final String CREATE_TEAM = "/createTeam";


    @PostMapping(CREATE_TEAM)
    public ResponseEntity<?> createTeam(@RequestBody @Valid CreateTeamRequest request) {
        return ResponseEntity.ok("created!");
    }


}
