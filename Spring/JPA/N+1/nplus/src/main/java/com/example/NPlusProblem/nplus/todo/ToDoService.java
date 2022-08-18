package com.example.NPlusProblem.nplus.todo;

import com.example.NPlusProblem.nplus.team.Team;
import com.example.NPlusProblem.nplus.team.TeamService;
import com.example.NPlusProblem.nplus.todo.dto.DtoOfCreateToDo;
import com.example.NPlusProblem.nplus.todo.dto.DtoOfCreatedToDo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final TeamService teamService;
    public DtoOfCreatedToDo createToDo(DtoOfCreateToDo createToDo, Long teamId){
        Team teamEntity = teamService.getTeam(teamId);
        ToDo todo = ToDo.builder()
                .taskDate(createToDo.getTaskDate())
                .taskName(createToDo.getTaskName())
                .build();
        ToDo toDoEntity = toDoRepository.save(todo);

        toDoEntity.updateTeam(teamEntity);


        return DtoOfCreatedToDo.builder()
                .taskDate(toDoEntity.getTaskDate())
                .taskName(toDoEntity.getTaskName())
                .teamId(toDoEntity.getTeam().getId())
                .build();
    }
}
