package com.ssafy.algoarium.UserRanking;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.algoarium.User.UserEntity;
import com.ssafy.algoarium.User.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rank")
public class UserRankingContoller {

	private final UserRankingService userRankingService;
	private final UserService userService;

	public static UserRankingDTO toDto(UserRankingEntity userRankingEntity){
		return UserRankingDTO.builder()
			.userRankingId(userRankingEntity.getUserRankingId())
			.userId(userRankingEntity.getUser().getUserId())
			.kakaoNickname(userRankingEntity.getUser().getKakaoNickname())
			.profileImage(userRankingEntity.getUser().getProfileImage())
			.score(userRankingEntity.getScore())
			.tier(userRankingEntity.getTier())
			.ranking(userRankingEntity.getRanking())
			.build();
	}

	@GetMapping("/{tier}")
	public List<UserRankingDTO> getRankingListByTier(@PathVariable int tier){
		List<UserRankingEntity> userRankingEntities = userRankingService.getListByTier(tier);
		System.out.println("/{tier}"+tier);
		return userRankingEntities.stream()
			.map(UserRankingContoller::toDto)
			.sorted(Comparator.comparingInt(UserRankingDTO::getScore).reversed())
			.toList();
	}

	@GetMapping("/top/{tier}")
	public UserRankingDTO getRankingTopByTier(@PathVariable int tier){
		System.out.println("/top/{tier}"+tier);
		return toDto(userRankingService.getTopUserRankingEntity(tier));
	}

	@GetMapping("/my/{userId}")
	public UserRankingDTO getMyRankingById(@PathVariable long userId){
		UserRankingEntity user = userRankingService.getRankingByUserId(userId);
		return UserRankingDTO.builder()
			.userId(user.getUser().getUserId())
			.userRankingId(user.getUserRankingId())
			.kakaoNickname(user.getUser().getKakaoNickname())
			.score(user.getScore())
			.tier(user.getTier())
			.profileImage(user.getUser().getProfileImage())
			.ranking(user.getRanking())
			.build();
	}
}
