package com.example.NPlusProblem.nplus.team;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("select distinct t from Team t left join fetch t.memberList")
    List<Team> findAllFetch();

    @EntityGraph(attributePaths = "memberList")
    @Query("select t from Team t")
    List<Team> findAllEntityGraph();


    Page<Team> findAll(Pageable pageable);
}
