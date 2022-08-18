package com.example.NPlusProblem.nplus.member;

import com.example.NPlusProblem.nplus.member.dto.DtoOfCreateMember;
import com.example.NPlusProblem.nplus.team.Team;
import com.example.NPlusProblem.nplus.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamService teamService;


    public Member createMember(DtoOfCreateMember dto){
        Member memberEntity = Member.builder()
                .age(dto.getAge())
                .name(dto.getName())
                .build();

        return memberRepository.save(memberEntity);
    }

    @Transactional
    public void participateTeam(Long teamId, Long memberId){
        Team teamEntity = teamService.getTeam(teamId);
        Member memberEntity = memberRepository.findById(memberId).get();

        memberEntity.participateTeam(teamEntity);


    }
}
