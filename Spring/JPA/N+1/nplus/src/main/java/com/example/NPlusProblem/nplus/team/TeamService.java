package com.example.NPlusProblem.nplus.team;

import com.example.NPlusProblem.nplus.member.dto.DtoOfgetMember;
import com.example.NPlusProblem.nplus.team.dto.DtoOfCreateTeam;
import com.example.NPlusProblem.nplus.team.dto.DtoOfGetTeams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public Team createTeam(DtoOfCreateTeam dtoOfCreateTeam){
        Team teamEntity = Team.builder()
                .name(dtoOfCreateTeam.getName())
                .build();

        return teamRepository.save(teamEntity);
    }

    public Team getTeam(Long teamId){
        return teamRepository.findById(teamId).get();
    }

    public List<DtoOfGetTeams> getTeamsByLazy(){
        System.out.println("----------------------");
        List<Team> teamList = teamRepository.findAll();
        System.out.println("----------------------");

        return teamList.stream()
                .map(v -> DtoOfGetTeams
                        .builder()
                        .memberList(v.memberList.stream()
                                .map(v2 -> DtoOfgetMember
                                        .builder()
                                        .age(v2.getAge())
                                        .name(v2.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .name(v.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DtoOfGetTeams> getTeamsByFetch(){

        System.out.println("---------------------");
        List<Team> teamList = teamRepository.findAllFetch();
        System.out.println("---------------------");


        return teamList.stream()
                .map(v -> DtoOfGetTeams
                        .builder()
                        .memberList(v.memberList.stream()
                                .map(v2 -> DtoOfgetMember
                                        .builder()
                                        .age(v2.getAge())
                                        .name(v2.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .name(v.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DtoOfGetTeams> getTeamsByEntityGraph(){
        System.out.println("--------------------------------");
        List<Team> teamList = teamRepository.findAllEntityGraph();
        System.out.println("--------------------------------");

        return teamList.stream()
                .map(v -> DtoOfGetTeams
                        .builder()
                        .memberList(v.memberList.stream()
                                .map(v2 -> DtoOfgetMember
                                        .builder()
                                        .age(v2.getAge())
                                        .name(v2.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .name(v.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DtoOfGetTeams> getTeamsByPageble(Pageable pageable){
        System.out.println("--------------------------------");
        Page<Team> teamList = teamRepository.findAll(pageable);
        System.out.println("--------------------------------");

        return teamList.stream()
                .map(v -> DtoOfGetTeams
                        .builder()
                        .memberList(v.memberList.stream()
                                .map(v2 -> DtoOfgetMember
                                        .builder()
                                        .age(v2.getAge())
                                        .name(v2.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .name(v.getName())
                        .build())
                .collect(Collectors.toList());
    }



}
