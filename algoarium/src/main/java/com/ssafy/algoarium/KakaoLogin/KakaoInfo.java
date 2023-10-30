package com.ssafy.algoarium.KakaoLogin;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoInfo {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("connected_at")
	private String connectedAt;

	@JsonProperty("properties")
	private KakaoProperties kakaoProperties;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;
}
@Data
class KakaoProperties{
	@JsonProperty("nickname")
	private String nickname;

	@JsonProperty("profile_image")
	private String profileImage;

	@JsonProperty("thumbnail_image")
	private String thumbnailImage;
}

@Data
class KakaoAccount{
	@JsonProperty("profile_nickname_needs_agreement")
	private boolean profileNicknameNeedsAgreement;

	@JsonProperty("profile_image_needs_agreement")
	private boolean profileImageNeedsAgreement;

	@JsonProperty("profile")
	private KakaoProfile kakaoProfile;

	@JsonProperty("has_email")
	private boolean hasEmail;

	@JsonProperty("email_needs_agreement")
	private boolean emailNeedsAgreement;

	@JsonProperty("is_email_valid")
	private boolean isEmailVaild;

	@JsonProperty("is_email_verified")
	private boolean isEmailVerified;

	@JsonProperty("email")
	private String email;
}

@Data
class KakaoProfile{
	@JsonProperty("nickname")
	private String nickname;

	@JsonProperty("thumbnail_image_url")
	private String thumbnailImageUrl;

	@JsonProperty("profile_image_url")
	private String profileImageUrl;

	@JsonProperty("is_default_image")
	private boolean isDefaultImage;
}