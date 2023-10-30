package com.ssafy.algoarium.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.algoarium.UserRanking.UserRankingEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByKakaoId(String kakaoId);
	UserEntity findByUserId(Long userId);
	UserEntity findBySolvedAcId(String solvedAcId);
	// 코드 추가 한 사람 : 김형진
	@Query("SELECT u.solvedAcId FROM UserEntity u")
	List<String> findAllSolvedAcIds();
}
