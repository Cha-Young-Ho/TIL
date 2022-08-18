package com.example.NPlusProblem.nplus.member;

import com.example.NPlusProblem.nplus.member.dto.DtoOfCreateMember;
import com.example.NPlusProblem.nplus.team.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/member")
    public ResponseEntity createMember(@RequestBody DtoOfCreateMember dtoOfCreateMember){
        Member memberEntity = memberService.createMember(dtoOfCreateMember);
        return new ResponseEntity(memberEntity, HttpStatus.OK);
    }

    @PostMapping("/api/participating/{teamId}/{memberId}")
    public ResponseEntity participateTeam(@PathVariable Long teamId, @PathVariable Long memberId){
        System.out.println(teamId);
        System.out.println(memberId);
        memberService.participateTeam(teamId, memberId);

        return new ResponseEntity("참가 완료", HttpStatus.OK);
    }
}
