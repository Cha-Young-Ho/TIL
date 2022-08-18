package com.example.NPlusProblem.nplus.team;

import com.example.NPlusProblem.nplus.member.Member;
import com.example.NPlusProblem.nplus.todo.ToDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    List<Member> memberList = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    List<ToDo> toDoList = new ArrayList<>();

    public List<Member> addMember(Member member){
        this.memberList.add(member);

        return this.memberList;
    }
}
