package com.example.NPlusProblem.nplus.todo.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DtoOfCreateToDo {

    private String taskName;
    private LocalDate taskDate;

}
