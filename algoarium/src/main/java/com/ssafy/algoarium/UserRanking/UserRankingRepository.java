package com.ssafy.algoarium.UserRanking;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.algoarium.User.UserEntity;

@Repository
public interface UserRankingRepository extends JpaRepository<UserRankingEntity, Long> {

	@Query(value = "SELECT ur FROM UserRankingEntity ur WHERE ur.tier = :tier")
	List<UserRankingEntity> findByTier(@Param("tier") int tier);

	@Query(value = "SELECT * FROM user_ranking WHERE tier = :tier ORDER BY score Desc LIMIT 1" , nativeQuery = true)
	Optional<UserRankingEntity> findTopByOrderByUserIdDesc(@Param("tier") int tier);

	@Query(value = "SELECT ur FROM UserRankingEntity ur WHERE ur.user = :user")
	Optional<UserRankingEntity> findByUserId(@Param("user") UserEntity user);

}
