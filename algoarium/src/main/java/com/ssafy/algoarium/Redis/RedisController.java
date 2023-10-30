package com.ssafy.algoarium.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/redis")
public class RedisController {

	@Autowired
	RedisService redisService;
	@ResponseBody
	@PostMapping("/save")
	public void RedisSave(RedisDto redisDto){

		redisService.saveByRedisDto(redisDto);

		System.out.printf("AccessToken : %s , RefreshToken : %s",redisDto.getAccessToken(), redisDto.getRefreshToken());

	}

	@RequestMapping("/test")
	public void TsetRedis(){
		RedisDto redisDto = RedisDto.builder()
			.accessToken("actk")
			.refreshToken("reftk")
			.build();

		redisService.saveByRedisDto(redisDto);

		System.out.printf("AccessToken : %s , RefreshToken : %s",redisDto.getAccessToken(), redisDto.getRefreshToken());

	}


}
