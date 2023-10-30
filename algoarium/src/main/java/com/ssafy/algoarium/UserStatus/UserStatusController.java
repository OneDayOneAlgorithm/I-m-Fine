package com.ssafy.algoarium.UserStatus;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@RequiredArgsConstructor
@RequestMapping("/my")
public class UserStatusController {

	private final UserStatusService userStatusService;

	public UserStatusDTO StatustoDto(UserStatusEntity userStatusEntity){
		return UserStatusDTO.builder()
			.userId(userStatusEntity.getUser().getUserId())
			.userStatus1(userStatusEntity.getUserStatus1())
			.userStatus2(userStatusEntity.getUserStatus2())
			.userStatus3(userStatusEntity.getUserStatus3())
			.userStatus4(userStatusEntity.getUserStatus4())
			.userStatus5(userStatusEntity.getUserStatus5())
			.build();
	}

	@GetMapping("/stat/{userId}")
	public UserStatusDTO getStatusById(@PathVariable long userId){
		System.out.println("---------------------------------");
		return StatustoDto(userStatusService.getStatusById(userId));
	}

	@GetMapping("/stat/average/{tier}")
	public UserStatusDTO getAvgStatueByTier(@PathVariable int tier){
		System.out.println(userStatusService.getAvgStatusByTier(tier).toString());
		return userStatusService.getAvgStatusByTier(tier);
	}


	@GetMapping("/stat/init/{baekjoonId}")
	public void initStatusSave(@PathVariable String baekjoonId){
		userStatusService.initStatus(baekjoonId);
	}

}
