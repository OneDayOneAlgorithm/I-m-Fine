package com.ssafy.algoarium.User;

import java.util.List;

import com.ssafy.algoarium.BaekjoonUser.BaekjoonUserDTO;
import com.ssafy.algoarium.BaekjoonUser.BaekjoonUserEntity;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.algoarium.Redis.RedisRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RedisRepository redisRepository;

	public Long saveUser(UserDto userDto){
		return (userRepository.save(UserEntity.builder()
			.kakaoId(userDto.getKakaoId())
			.kakaoNickname(userDto.getKakaoNickname())
			.refreshToken(userDto.getRefreshToken())
			.profileImage(userDto.getProfileImage())
			.preTier(0)
			.solvedAcId(userDto.getSolvedAcId())
			.build()).getUserId());
	}


	@Transactional
	public UserEntity updateUser(UserDto userDto){
		UserEntity userEntity = userRepository.findByKakaoId(userDto.getKakaoId());

		UserEntity updateUserEntity = UserEntity.builder()
			.userId(userEntity.getUserId())
			.kakaoId(userEntity.getKakaoId())
			.kakaoNickname(userDto.getKakaoNickname())
			.preTier(userEntity.getPreTier())
			.profileImage(userDto.getProfileImage())
			.refreshToken(userDto.getRefreshToken())
			.solvedAcId(userDto.getSolvedAcId())
			.build();

		userRepository.save(updateUserEntity);
		return updateUserEntity;
	}
	@Transactional
	public UserEntity getUserById(long userId){
		return userRepository.findById(userId).orElseThrow(()
			-> new EntityNotFoundException("not found"));

	}

	@Transactional
	public UserEntity getUserByEmail(String email){
		return userRepository.findByKakaoId(email);
	}

	@Transactional
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public void deleteUser(long userId) {
		userRepository.deleteById(userId);
	}

	@Transactional
	public String isCodeEquals(long userId, String solvedAc, String code){
		// 유저 조회 URL
		String apiUrl = "https://solved.ac/api/v3/user/show?handle=";

		// RestTemplate을 사용하여 API 호출
		RestTemplate restTemplate = new RestTemplate();
		String apiResponse = restTemplate.getForObject(apiUrl + solvedAc, String.class);

		try {
			JSONObject jsonObject = new JSONObject(apiResponse);
			String bio = jsonObject.getString("bio");
			if (bio.equals(code)){
				UserEntity userEntity = userRepository.findByUserId(userId);
				// UserEntity의 builder를 사용하여 solvedAcId 필드를 업데이트
				userEntity = UserEntity.builder()
						.userId(userEntity.getUserId())
						.kakaoId(userEntity.getKakaoId())
						.kakaoNickname(userEntity.getKakaoNickname())
						.profileImage(userEntity.getProfileImage())
						.preTier(userEntity.getPreTier())
						.refreshToken(userEntity.getRefreshToken())
						.solvedAcId(solvedAc) // solvedAcId 필드를 업데이트
						.build();

				// 변경된 엔티티를 저장
				userRepository.save(userEntity);
				return "true";
			}
			return "false";


		} catch (Exception e) {
			// 에러가 발생하면 예외 처리
			e.printStackTrace(); // 에러 로그 출력 (나중에 수정하세요).
			return "false";
		}
	}
}
