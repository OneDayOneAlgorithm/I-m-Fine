package com.ssafy.algoarium.UserStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.algoarium.User.UserEntity;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatusEntity, Long> {

	@Query(value = "SELECT user_status.user_status_id " +
			", user_status.wis, user_status.con, user_status.str, user_status.luk, user_status.sma, " +
			"user_status.user_id " +
			"FROM user_status " +
			"JOIN user ON user.user_id = user_status.user_id " +
			"JOIN user_ranking ON user.user_id = user_ranking.user_id " +
			"WHERE tier = :tier" , nativeQuery = true)
	List<UserStatusEntity> findAvgByTier(@Param("tier") int tier);

	UserStatusEntity findByUser(UserEntity user);
}
