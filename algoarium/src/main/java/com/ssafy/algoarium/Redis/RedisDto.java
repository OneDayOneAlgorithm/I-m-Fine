package com.ssafy.algoarium.Redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@RedisHash(value = "redisCrud", timeToLive = 60*60)
public class RedisDto {

	private String accessToken;
	@Id
	private String refreshToken;

	@Builder
	public RedisDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
