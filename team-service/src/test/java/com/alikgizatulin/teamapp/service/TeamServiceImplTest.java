package com.alikgizatulin.teamapp.service;

import com.alikgizatulin.teamapp.entity.Team;
import com.alikgizatulin.teamapp.exception.TeamNotFoundException;
import com.alikgizatulin.teamapp.repository.TeamRepository;
import com.alikgizatulin.teamapp.service.impl.TeamServiceImpl;
import com.alikgizatulin.teamapp.util.TeamUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    @DisplayName("Test getTeamById() method when team is exists ")
    void getTeamById_whenExists_returnTeamEntity() {
        //given
        UUID teamId = UUID.randomUUID();
        when(this.teamRepository.findById(teamId))
                .thenReturn(Optional.of(TeamUtils.getTestTeamByTeamId(teamId)));

        //when
        Team team = this.teamService.getTeamById(teamId);

        //then
        assertNotNull(team);
        assertEquals(team.getId(),teamId);

        verify(teamRepository).findById(teamId);
    }

    @Test
    @DisplayName("Test getTeamById() method when team not found")
    void getTeamById_whenNotExists_throwTeamNotFoundException() {
        //given
        UUID teamId = UUID.randomUUID();
        when(this.teamRepository.findById(teamId))
                .thenReturn(Optional.empty());

        //when & then
        TeamNotFoundException exception = assertThrows(
                TeamNotFoundException.class, () ->
                        this.teamService.getTeamById(teamId)
        );

        verify(teamRepository).findById(teamId);
    }
}
