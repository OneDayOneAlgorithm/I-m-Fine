package com.ssafy.algoarium.SolvedProblemHistory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedProblemHistoryRepository extends JpaRepository<SolvedProblemHistoryEntity, Long> {
	Optional<SolvedProblemHistoryEntity> findByUserUserIdAndProblemProblemId(Long userId, Integer problemId);
}
