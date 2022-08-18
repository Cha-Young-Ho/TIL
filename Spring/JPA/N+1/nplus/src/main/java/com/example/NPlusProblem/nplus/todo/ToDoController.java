package com.example.NPlusProblem.nplus.todo;

import com.example.NPlusProblem.nplus.common.Response;
import com.example.NPlusProblem.nplus.todo.dto.DtoOfCreateToDo;
import com.example.NPlusProblem.nplus.todo.dto.DtoOfCreatedToDo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ToDoController {

    private final ToDoService toDoService;

    @PostMapping("/api/todo/{teamId}")
    public ResponseEntity createToDo(@PathVariable Long teamId, @RequestBody DtoOfCreateToDo dtoOfCreateToDo){
        DtoOfCreatedToDo dto = toDoService.createToDo(dtoOfCreateToDo, teamId);
        Response response = Response.builder()
                .content(dto)
                .build();

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
