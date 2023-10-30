package com.ssafy.algoarium.UserRanking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.algoarium.User.UserDto;
import com.ssafy.algoarium.User.UserEntity;
import com.ssafy.algoarium.User.UserRepository;
import com.ssafy.algoarium.User.UserService;
import com.ssafy.algoarium.UserStatus.UserStatusDTO;
import com.ssafy.algoarium.UserStatus.UserStatusEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRankingService {

	private final UserRankingRepository userRankingRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	public UserRankingEntity toEntity(UserRankingDTO userRankingDTO , UserEntity userEntity){
		return UserRankingEntity.builder()
			.user(userEntity)
			.ranking(userRankingDTO.getRanking())
			.score(userRankingDTO.getScore())
			.tier(userRankingDTO.getTier())
			.build();
	}

	public List<UserRankingEntity> getListByTier(int tier){
		return userRankingRepository.findByTier(tier);
	}

	public UserRankingEntity getRankingByUserId(long userId){
		UserEntity userEntity = userService.getUserById(userId);
		return userRankingRepository.findByUserId(userEntity).get();
	}
	public UserRankingEntity getTopUserRankingEntity(int tier){
		return userRankingRepository.findTopByOrderByUserIdDesc(tier).get();
	}

	public int getLengthUserRanking(){
		return userRankingRepository.findAll().size();
	}

	public UserRankingEntity save(UserRankingEntity userRankingEntity){
		userRankingRepository.save(userRankingEntity);
		return userRankingEntity;
	}

	public UserRankingEntity saveInit(Long userId){
		return userRankingRepository.save(UserRankingEntity
			.builder()
			.user(userService.getUserById(userId))
				.ranking(getLengthUserRanking()+1)
				.score(0)
				.tier(1)
			.build());
	}



}
