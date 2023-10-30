package com.ssafy.algoarium.User;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.algoarium.KakaoLogin.KakaoDto;
import com.ssafy.algoarium.KakaoLogin.KakaoInfo;
import com.ssafy.algoarium.KakaoLogin.KakaoLoginService;
import com.ssafy.algoarium.Redis.RedisDto;
import com.ssafy.algoarium.Redis.RedisService;
import com.ssafy.algoarium.UserRanking.UserRankingEntity;
import com.ssafy.algoarium.UserRanking.UserRankingService;
import com.ssafy.algoarium.UserStatus.UserStatusService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	//accessToken, refreshToken, 만료시간 2개를 준다 ->
	//다시 카카오 API 에 요청해서 KakaoUser Data 를 받고
	// 이를 UserDto로 변환 후 DB 에 있는지 찾는다
	//-> 없다면 새로 만들고 로그인 진행
	// 로그인
	// 이 유저에 맞는 AccessToken을 key로 하여, 만료시간을 설정 한 후 Redis에 저장
	// 요청이 있을 때

	private final UserService userService;
	private final KakaoLoginService kakaoLoginService;
	private final RedisService redisService;
	private final UserRepository userRepository;
	private final UserRankingService userRankingService;
	private final UserStatusService userStatusService;


	@GetMapping("/login/{accessToken}/{refreshToken}")
	public UserDto loginUser(@PathVariable String accessToken, @PathVariable String refreshToken){
		System.out.println("로그인 시도");
		KakaoInfo profile = kakaoLoginService.findKakaoInfo(accessToken);
		KakaoDto profileDto = kakaoLoginService.sendKakaoDto(profile);
		UserDto answerDto;

		long userId;

		if(userService.getUserByEmail(profileDto.getEmail()) == null){
			answerDto  = UserDto.builder()
					.kakaoId(profileDto.getEmail())
					.kakaoNickname(profileDto.getName())
					.profileImage(profileDto.getProfileUrl())
					.refreshToken(refreshToken)
					.preTier(1)
					.tier(1)
					.solvedAcId("")
					.build();
			userId = userService.saveUser(answerDto);

			userRankingService.saveInit(userId);
			userStatusService.saveInit(userId);
		}

		// redisService.saveByRedisDto(RedisDto.builder()
		//     .accessToken(accessToken)
		//     .refreshToken(refreshToken).build());
		else {
			UserDto answerUserDto = userService.getUserByEmail(profileDto.getEmail()).toUserDto();
			UserRankingEntity userRankingEntity = userRankingService.getRankingByUserId(answerUserDto.getUserId());

			return UserDto.builder()
					.userId(answerUserDto.getUserId())
					.kakaoId(answerUserDto.getKakaoId())
					.kakaoNickname(answerUserDto.getKakaoNickname())
					.profileImage(answerUserDto.getProfileImage())
					.preTier(answerUserDto.getPreTier())
					.refreshToken(answerUserDto.getRefreshToken())
					.tier(userRankingEntity.getTier())
					.solvedAcId(answerUserDto.getSolvedAcId())
					.build();



		}

		return answerDto;
	}
}
