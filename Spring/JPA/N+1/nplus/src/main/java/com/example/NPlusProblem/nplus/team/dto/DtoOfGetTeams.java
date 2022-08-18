package com.example.NPlusProblem.nplus.team.dto;

import com.example.NPlusProblem.nplus.member.dto.DtoOfgetMember;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DtoOfGetTeams {

    private List<DtoOfgetMember> memberList;
    private String name;
}
