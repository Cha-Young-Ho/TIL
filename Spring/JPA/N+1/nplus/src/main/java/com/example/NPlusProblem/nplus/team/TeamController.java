package com.example.NPlusProblem.nplus.team;

import com.example.NPlusProblem.nplus.common.Response;
import com.example.NPlusProblem.nplus.team.dto.DtoOfCreateTeam;
import com.example.NPlusProblem.nplus.team.dto.DtoOfGetTeams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/api/team")
    public ResponseEntity createTeam(@RequestBody DtoOfCreateTeam dtoOfCreateTeam){
        Team teamEntity = teamService.createTeam(dtoOfCreateTeam);

        return new ResponseEntity(teamEntity, HttpStatus.OK);
    }

    @GetMapping("/api/teams/lazy")
    public ResponseEntity getTeamsByLazy(){
        Response response = Response.builder()
                .content(teamService.getTeamsByLazy())
                .build();

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/api/teams/fetch")
    public ResponseEntity getTeamsByFetch(){
        Response response = Response.builder()
                .content(teamService.getTeamsByFetch())
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/api/teams/entity-graph")
    public ResponseEntity getTeamsByEntityGraph(){

        Response response = Response.builder()
                .content(teamService.getTeamsByEntityGraph())
                .build();

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/api/teams/page")
    public ResponseEntity getTeamsPage(Pageable pageable){
        List<DtoOfGetTeams> teamList = teamService.getTeamsByPageble(pageable);

        Response response = Response.builder()
                .content(teamList)
                .build();

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
