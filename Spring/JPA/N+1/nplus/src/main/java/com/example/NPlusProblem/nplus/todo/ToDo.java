package com.example.NPlusProblem.nplus.todo;

import com.example.NPlusProblem.nplus.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "TASK_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Team team;

    public void updateTeam(Team team){
        this.team = team;
    }
}
