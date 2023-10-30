package com.ssafy.algoarium.BaekjoonUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaekjoonUserRepository extends JpaRepository<BaekjoonUserEntity, Integer> {
    BaekjoonUserEntity findByBjId(String bjId);
    @Query("SELECT bj.bjId FROM BaekjoonUserEntity bj")
    List<String> findAllBjIds();
}
