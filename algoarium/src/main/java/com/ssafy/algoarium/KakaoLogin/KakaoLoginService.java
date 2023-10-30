package com.ssafy.algoarium.KakaoLogin;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoLoginService {

	public KakaoOauthToken findKakaoToken(String code){

		String callbackUrl = "http://j9d204.p.ssafy.io:8090/auth/kakao/callback";
		String codeUrl = "https://kauth.kakao.com/oauth/token";

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type" , "authorization_code");
		params.add("client_id" , "1e6e787538b273266b9e8054397aec13");
		params.add("redirect_uri",callbackUrl);
		params.add("code",code);

		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
			new HttpEntity<>(params,headers);

		ResponseEntity<String> response = rt.exchange(
			codeUrl
			, HttpMethod.POST
			,kakaoTokenRequest
			,String.class
		);

		//Gson , Json, Simple, Object Mapper
		ObjectMapper objectMapper = new ObjectMapper();
		KakaoOauthToken kakaoOauthToken =null;
		try {
			kakaoOauthToken = objectMapper.readValue(response.getBody(),KakaoOauthToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return kakaoOauthToken;
	}

	public KakaoInfo findKakaoInfo(String accessToken){

		String tokenUrl = "https://kapi.kakao.com/v2/user/me";


		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Bearer "+accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
			new HttpEntity<>(null,headers);

		ResponseEntity<String> response = rt.exchange(
			tokenUrl
			, HttpMethod.POST
			,kakaoTokenRequest
			,String.class
		);

		//Gson , Json, Simple, Object Mapper
		ObjectMapper objectMapper = new ObjectMapper();
		KakaoInfo kakaoInfo =null;
		try {
			kakaoInfo = objectMapper.readValue(response.getBody(),KakaoInfo.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return kakaoInfo;
	}

	public KakaoDto sendKakaoDto(KakaoInfo kakaoInfo){

		KakaoDto kakaoDto = KakaoDto.builder()
			.email(kakaoInfo.getKakaoAccount().getEmail())
			.name(kakaoInfo.getKakaoAccount().getKakaoProfile().getNickname())
			.profileUrl(kakaoInfo.getKakaoAccount().getKakaoProfile().getProfileImageUrl())
			.build();

		return kakaoDto;
	}


}
