package com.ssafy.algoarium.KakaoLogin;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class KakaoDto {

	String email;

	String name;

	String profileUrl;

	@Builder
	public KakaoDto(String email, String name, String profileUrl) {
		this.email = email;
		this.name = name;
		this.profileUrl = profileUrl;
	}
}
