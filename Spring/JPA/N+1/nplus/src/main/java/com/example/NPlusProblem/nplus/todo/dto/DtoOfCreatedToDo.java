package com.example.NPlusProblem.nplus.todo.dto;

import com.example.NPlusProblem.nplus.team.dto.DtoOfGetTeams;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DtoOfCreatedToDo {

    private String taskName;

    private LocalDate taskDate;

    private Long teamId;
}
