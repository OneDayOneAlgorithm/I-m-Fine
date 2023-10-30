package com.ssafy.algoarium.ProblemLike;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemLikeRepository extends JpaRepository<ProblemLikeEntity, Integer> {
    ProblemLikeEntity findByProblemEntityAndUserEntity(ProblemEntity problemEntity, UserEntity userEntity);
    List<ProblemLikeEntity> findByUserEntityUserIdAndLikeTypeIsTrue(Long userId);
    ProblemLikeEntity findByUserEntityUserIdAndProblemEntityProblemId(Long userID, Integer problemId);
}

