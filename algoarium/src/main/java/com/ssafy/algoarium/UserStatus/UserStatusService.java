package com.ssafy.algoarium.UserStatus;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.algoarium.Problem.ProblemRepository;
import com.ssafy.algoarium.SolvedProblemHistory.SolvedProblemHistoryRepository;
import com.ssafy.algoarium.SolvedProblemHistory.SolvedResponse;
import com.ssafy.algoarium.User.UserEntity;
import com.ssafy.algoarium.User.UserRepository;
import com.ssafy.algoarium.User.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserStatusService {

	private final UserStatusRepository userStatusRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final SolvedProblemHistoryRepository solvedProblemHistoryRepository;
	private final ProblemRepository problemRepository;

	public UserStatusEntity toEntity(UserStatusDTO userStatusDTO){
		return UserStatusEntity.builder()
			.userStatusId(userStatusDTO.getUserStatusId())
			.user(userRepository.findById(userStatusDTO.getUserId()).get())
			.userStatus1(userStatusDTO.getUserStatus1())
			.userStatus2(userStatusDTO.getUserStatus2())
			.userStatus3(userStatusDTO.getUserStatus3())
			.userStatus4(userStatusDTO.getUserStatus4())
			.userStatus5(userStatusDTO.getUserStatus5())
			.build();
	}

	@Transactional
	public UserStatusEntity getStatusById(long userId){;
		UserStatusEntity userStatusEntity = userStatusRepository.findByUser(userRepository.findByUserId(userId));
		// userStatusDTO.ifPresent();
		return userStatusEntity;
	}

	@Transactional
	public UserStatusDTO getAvgStatusByTier(int tier){

		List<UserStatusEntity> userStatusEntityList = userStatusRepository.findAvgByTier(tier);

		int length = userStatusEntityList.size();

		int wis = 0;
		int con = 0;
		int str = 0;
		int luk = 0;
		int sma = 0;

		for(UserStatusEntity entity : userStatusEntityList){
			wis += entity.getUserStatus1();
			con += entity.getUserStatus2();
			str += entity.getUserStatus3();
			luk += entity.getUserStatus4();
			sma += entity.getUserStatus5();
		}

		return UserStatusDTO.builder()
				.userStatus1(wis/length)
				.userStatus2(con/length)
				.userStatus3(str/length)
				.userStatus4(luk/length)
				.userStatus5(sma/length).build();
	}


	@Transactional
	public void updateStatus(UserStatusDTO userStatusDTO){
		userStatusRepository.save(toEntity(userStatusDTO));
	}


	@Transactional
	public long saveInit(long userId){
		UserStatusEntity userStatusEntity = UserStatusEntity.builder()
			.user(userService.getUserById(userId))
			.userStatus1(1)
			.userStatus2(1)
			.userStatus3(1)
			.userStatus4(1)
			.userStatus5(1)
			.build();

		userStatusRepository.save(userStatusEntity);

		return userStatusEntity.getUser().getUserId();
	}

	@Transactional
	public void initStatus(String baekjoonId){

		URI uri = UriComponentsBuilder
			.fromUriString("https://solved.ac/api/v3/user/show")
			.queryParam("handle" , baekjoonId)
			.encode()
			.build()
			.toUri();

		System.out.println(uri.toString());

		RestTemplate rt = new RestTemplate();

		ResponseEntity<String> result = rt.getForEntity(uri, String.class);

		ObjectMapper objectMapper = new ObjectMapper();

		UserStatusResponse solvedResponse = null;

		try {
			solvedResponse = objectMapper.readValue(result.getBody(), UserStatusResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		UserEntity user = userRepository.findBySolvedAcId(baekjoonId);

		System.out.println(user.getUserId());

		UserStatusEntity userStatusEntity = userStatusRepository.findByUser(user);

		System.out.println(userStatusEntity.getUser());

		userStatusEntity.setUserStatus1(solvedResponse.getSolvedCount()/100 + 20);
		userStatusEntity.setUserStatus2(solvedResponse.getStardustsCount()/150);
		userStatusEntity.setUserStatus3(solvedResponse.getMaxStreakCount()/30 + 30);
		userStatusEntity.setUserStatus4(solvedResponse.getRatingBySolvedCount()/5 +10);
		userStatusEntity.setUserStatus5(solvedResponse.getRating()/100 + 20);

		userStatusRepository.save(userStatusEntity);
		System.out.println(solvedResponse.getSolvedCount());
		System.out.println(solvedResponse.getStardustsCount());
		System.out.println("save!!!!!!!");
	}




}
