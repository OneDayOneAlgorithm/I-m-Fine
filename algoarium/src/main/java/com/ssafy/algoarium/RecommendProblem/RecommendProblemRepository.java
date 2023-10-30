package com.ssafy.algoarium.RecommendProblem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendProblemRepository extends JpaRepository<RecommendProblemEntity, Integer> {

    @Query("SELECT rp FROM RecommendProblemEntity rp WHERE rp.userEntity.userId = :userId AND rp.type = 0")
    List<RecommendProblemEntity> findStrongRecommendations(@Param("userId") Integer userId);

    @Query("SELECT rp FROM RecommendProblemEntity rp WHERE rp.userEntity.userId = :userId AND rp.type = 1")
    List<RecommendProblemEntity> findWeakRecommendations(@Param("userId") Integer userId);

    @Query("SELECT rp FROM RecommendProblemEntity rp WHERE rp.userEntity.userId = :userId AND rp.type = 2")
    List<RecommendProblemEntity> findSimilarRecommendations(@Param("userId") Integer userId);
}
